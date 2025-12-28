package com.example.patient.Management.service.impl;

import com.example.patient.Customer.entity.ServiceEscort;
import com.example.patient.Customer.entity.ServiceOrders;
import com.example.patient.Customer.mapper.ServiceComplaintMapper;
import com.example.patient.Customer.mapper.ServiceEscortMapper;
import com.example.patient.Customer.mapper.ServiceEvaluationMapper;
import com.example.patient.Customer.mapper.ServiceOrdersMapper;
import com.example.patient.DTO.Command.UpdateAdminCommand;
import com.example.patient.DTO.DTO.OrderTrendDTO;
import com.example.patient.DTO.VO.*;
import com.example.patient.H5.entity.UserPatient;
import com.example.patient.H5.mapper.UserPatientMapper;
import com.example.patient.Management.mapper.BaseDepartmentMapper;
import com.example.patient.Management.mapper.BaseHospitalMapper;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.processor.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Management.entity.ManagementAdmin;
import com.example.patient.Management.mapper.ManagementAdminMapper;
import com.example.patient.Management.service.ManagementAdminService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;





import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 平台管理员表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Slf4j
@Service
public class ManagementAdminServiceImpl extends ServiceImpl<ManagementAdminMapper, ManagementAdmin>
        implements ManagementAdminService{
    @Resource
    private ManagementAdminMapper managementAdminMapper;

    @Resource
    private UserPatientMapper userPatientMapper;

    @Resource
    private ServiceEscortMapper serviceEscortMapper;

    @Resource
    private ServiceOrdersMapper serviceOrdersMapper;

    @Resource
    private ServiceComplaintMapper serviceComplaintMapper;

    @Resource
    private ServiceEvaluationMapper serviceEvaluationMapper;

    @Override
    public Boolean addAdmin(ManagementAdmin managementAdmin) {
        if (managementAdmin == null
                || StrUtil.isBlank(managementAdmin.getUsername())
                || StrUtil.isBlank(managementAdmin.getPassword())
                || StrUtil.isBlank(managementAdmin.getRealName())
                || StrUtil.isBlank(managementAdmin.getPhone())) {
            return false;
        }

        // 唯一性校验
        boolean usernameExists = this.count(QueryWrapper.create()
                .eq(ManagementAdmin::getUsername, managementAdmin.getUsername())) > 0;
        boolean phoneExists = this.count(QueryWrapper.create()
                .eq(ManagementAdmin::getPhone, managementAdmin.getPhone())) > 0;

        if (usernameExists || phoneExists) {
            return false;
        }

        // 默认值

        managementAdmin.setGmtCreate(LocalDateTime.now());
        managementAdmin.setGmtModified(LocalDateTime.now());

        return managementAdminMapper.insert(managementAdmin) > 0;
    }

    @Override
    public Boolean deleteAdmin(Long adminId) {
        return LogicDeleteManager.execWithoutLogicDelete(()->
                managementAdminMapper.deleteById(adminId)
        ) >0;
    }

    @Override
    public Boolean updateAdmin(UpdateAdminCommand command) {


        ManagementAdmin admin = ManagementAdmin.builder()
                .id(command.getId())
                .username(command.getUsername())
                .password(command.getPassword())
                .phone(command.getPhone())
                .build();
        return managementAdminMapper.update(admin) > 0;
    }


    @Override
    public ManagementAdmin getAdmin(ManagementAdmin managementAdmin) {
        QueryWrapper wrapper =QueryWrapper.create()
                .eq("id",managementAdmin.getId())
                .eq("username",managementAdmin.getUsername())
                .eq("phone",managementAdmin.getPhone());
        return managementAdminMapper.selectOneByQuery(wrapper);
    }

    @Override
    public List<ManagementAdmin> listAdmins(ManagementAdmin managementAdmin) {
        QueryWrapper wrapper = QueryWrapper.create()
                .eq("real_name",managementAdmin.getRealName())

                .between("gmt_create",managementAdmin.getGmtCreate(),managementAdmin.getGmtModified());
        return managementAdminMapper.selectListByQuery(wrapper);
    }

    @Override
    public ManagementAdmin loginByPhoneAndPassword(String phone, String password) {
        if (StrUtil.isBlank(phone) || StrUtil.isBlank(password)) {
            return null;
        }
        // 1. 查手机号
        ManagementAdmin admin = this.mapper.selectOneByQuery(
                QueryWrapper.create().eq(ManagementAdmin::getPhone, phone));
        if (admin == null) {
            return null;
        }
        // 2. 直接比较明文密码（取消BCrypt加密验证）
        String dbPassword = admin.getPassword();
        boolean match = password.equals(dbPassword);
        if (!match) {
            return null;
        }
        // 3. 脱敏后返回
        admin.setPassword(null);
        return admin;
    }

    /**
     * 启用/禁用患者账号
     *
     * @param patientId
     * @param status
     */
    @Override
    public Boolean togglePatientStatus(Integer patientId, Integer status) {
        try {
            if (patientId == null || status == null) {
                return false;
            }

            UserPatient patient = userPatientMapper.selectOneById(patientId);
            if (patient == null) {
                return false;
            }

            UserPatient updatePatient = UserPatient.builder()
                    .patientId(patientId)
                    .status(status)
                    .build();

            boolean updated = userPatientMapper.update(updatePatient) > 0;
            if (updated) {
                log.info("管理员修改患者状态：患者ID={}, 新状态={}", patientId, status);
            }
            return updated;

        } catch (Exception e) {
            log.error("修改患者状态失败：患者ID={}", patientId, e);
            return false;
        }
    }

    /**
     * 启用/禁用陪诊员账号
     *
     * @param escortId
     * @param status
     */
    @Override
    public Boolean toggleEscortStatus(Integer escortId, Integer status) {
        try {
            if (escortId == null || status == null) {
                return false;
            }

            ServiceEscort escort = serviceEscortMapper.selectOneById(escortId);
            if (escort == null) {
                return false;
            }

            ServiceEscort updateEscort = ServiceEscort.builder()
                    .escortId(escortId)
                    .accountStatus(status)
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean updated = serviceEscortMapper.update(updateEscort) > 0;
            if (updated) {
                log.info("管理员修改陪诊员状态：陪诊员ID={}, 新状态={}", escortId, status);
            }
            return updated;

        } catch (Exception e) {
            log.error("修改陪诊员状态失败：陪诊员ID={}", escortId, e);
            return false;
        }
    }

    /**
     * 审核陪诊员认证（通过/驳回）
     *
     * @param escortId
     * @param auditStatus
     * @param auditNote
     */
    @Override
    public Boolean auditEscort(Integer escortId, Integer auditStatus, String auditNote) {
        try {
            if (escortId == null || auditStatus == null) {
                return false;
            }

            ServiceEscort escort = serviceEscortMapper.selectOneById(escortId);
            if (escort == null) {
                return false;
            }

            ServiceEscort updateEscort = ServiceEscort.builder()
                    .escortId(escortId)
                    .auditStatus(auditStatus)
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean updated = serviceEscortMapper.update(updateEscort) > 0;
            if (updated) {
                log.info("管理员审核陪诊员：陪诊员ID={}, 审核状态={}, 备注={}",
                        escortId, auditStatus, auditNote);
            }
            return updated;

        } catch (Exception e) {
            log.error("审核陪诊员失败：陪诊员ID={}", escortId, e);
            return false;
        }
    }

    /**
     * 将陪诊员加入/移出黑名单
     *
     * @param escortId
     * @param accountStatus
     * @param reason
     */
    @Override
    public Boolean manageEscortBlacklist(Integer escortId, Integer accountStatus, String reason) {
        try {
            if (escortId == null || accountStatus == null) {
                return false;
            }

            ServiceEscort escort = serviceEscortMapper.selectOneById(escortId);
            if (escort == null) {
                return false;
            }

            ServiceEscort updateEscort = ServiceEscort.builder()
                    .escortId(escortId)
                    .accountStatus(accountStatus)
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean updated = serviceEscortMapper.update(updateEscort) > 0;
            if (updated) {
                log.info("管理员管理陪诊员黑名单：陪诊员ID={}, 状态={}, 原因={}",
                        escortId, accountStatus, reason);
            }
            return updated;

        } catch (Exception e) {
            log.error("管理陪诊员黑名单失败：陪诊员ID={}", escortId, e);
            return false;
        }
    }

    /**
     * 分页查询所有患者
     *
     * @param pageNum
     * @param pageSize
     * @param keyword
     */
    @Override
    public Page<UserPatientVO> listAllPatients(Integer pageNum, Integer pageSize, String keyword) {
        try {
            Page<UserPatientVO> page = new Page<>(pageNum, pageSize);

            QueryWrapper queryWrapper = QueryWrapper.create()
                    .select("up.*")
                    .select("(SELECT COUNT(*) FROM service_orders so WHERE so.patient_id = up.patient_id) AS order_count")
                    .select("(SELECT COUNT(*) FROM service_complaint sc WHERE sc.complainant_id = up.patient_id) AS complaint_count")
                    .from("user_patient up");

            if (StringUtils.isNotBlank(keyword)) {
                queryWrapper.where("up.real_name LIKE ? OR up.phone LIKE ? OR up.username LIKE ?",
                        "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
            }

            queryWrapper.orderBy("up.gmt_create", false);

            return userPatientMapper.paginateAs(page, queryWrapper, UserPatientVO.class);

        } catch (Exception e) {
            log.error("查询患者列表失败", e);
            return new Page<>();
        }
    }

    /**
     * 分页查询所有陪诊员
     *
     * @param pageNum
     * @param pageSize
     * @param keyword
     */
    @Override
    public Page<EscortInfoVO> listAllEscorts(Integer pageNum, Integer pageSize, String keyword) {
        try {
            Page<EscortInfoVO> page = new Page<>(pageNum, pageSize);

            QueryWrapper queryWrapper = QueryWrapper.create()
                    .select("se.*")
                    .select("(SELECT COUNT(*) FROM service_orders so WHERE so.escort_id = se.escort_id) AS order_count")
                    .select("(SELECT COUNT(*) FROM service_orders so WHERE so.escort_id = se.escort_id AND so.status = 2) AS completed_count")
                    .select("(SELECT COUNT(*) FROM service_complaint sc WHERE sc.respondent_id = se.escort_id) AS complaint_count")
                    .select("(SELECT AVG(score) FROM service_evaluation se2 WHERE se2.escort_id = se.escort_id) AS avg_score")
                    .from("service_escort se");

            if (StringUtils.isNotBlank(keyword)) {
                queryWrapper.where("se.real_name LIKE ? OR se.phone LIKE ? OR se.username LIKE ?",
                        "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
            }

            queryWrapper.orderBy("se.gmt_create", false);

            return serviceEscortMapper.paginateAs(page, queryWrapper, EscortInfoVO.class);

        } catch (Exception e) {
            log.error("查询陪诊员列表失败", e);
            return new Page<>();
        }
    }

    /**
     * 数据统计：按日/周/月输出核心指标
     */
    /**
     * 数据统计：按日/周/月输出核心指标
     */
    @Override
    public StatisticsVO getStatistics(String timeType,
                                      LocalDateTime startTime,
                                      LocalDateTime endTime) {
        try {
            /* 1. 如果调用方没传起止时间，就按 timeType 自动补全 */
            if (startTime == null || endTime == null) {
                LocalDateTime[] range = getTimeRange(timeType);
                startTime = range[0];
                endTime   = range[1];
            }

            /* 2. 订单维度统计 */
            QueryWrapper oWrapper = QueryWrapper.create()
                    .select("COUNT(*)               AS total_orders")
                    .select("SUM(price)             AS total_amount")
                    .select("SUM(CASE WHEN status = 2 THEN 1 ELSE 0 END) AS completed_orders")
                    .select("SUM(CASE WHEN status IN (0,1) THEN 1 ELSE 0 END) AS ongoing_orders")
                    .select("SUM(CASE WHEN status = 4 THEN 1 ELSE 0 END) AS complaint_orders")
                    .from("service_orders")
                    .between("gmt_create", startTime, endTime);
            Map<String, Object> oMap = serviceOrdersMapper.selectOneByQueryAs(oWrapper, Map.class);

            /* 3. 用户维度统计 */
            long registeredPatients = userPatientMapper.selectCountByQuery(
                    QueryWrapper.create().between("gmt_create", startTime, endTime));
            long registeredEscorts  = serviceEscortMapper.selectCountByQuery(
                    QueryWrapper.create().between("gmt_create", startTime, endTime));

            /* 4. 平均满意度（评分表） */
            BigDecimal avgSatisfaction = serviceEvaluationMapper.selectOneByQueryAs(
                    QueryWrapper.create()
                            .select("AVG(score) AS avg_score")
                            .from("service_evaluation")
                            .between("gmt_create", startTime, endTime),
                    BigDecimal.class);
            if (avgSatisfaction == null) avgSatisfaction = BigDecimal.ZERO;

            /* 5. 投诉率 */
            long totalOrders     = ((Number) oMap.get("total_orders")).longValue();
            long complaintOrders = ((Number) oMap.get("complaint_orders")).longValue();
            BigDecimal complaintRate = totalOrders == 0 ? BigDecimal.ZERO :
                    BigDecimal.valueOf(complaintOrders)
                            .divide(BigDecimal.valueOf(totalOrders), 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));   // 百分比

            /* 6. 订单趋势图 */
            List<OrderTrendVO> trend = getOrderTrendData(timeType, startTime, endTime);

            /* 7. 组装返回 */
            return StatisticsVO.builder()
                    .timeType(timeType)
                    .startTime(startTime)
                    .endTime(endTime)
                    .totalOrders((int) totalOrders)
                    .totalAmount((BigDecimal) oMap.get("total_amount"))
                    .completedOrders(((Number) oMap.get("completed_orders")).intValue())
                    .ongoingOrders(((Number) oMap.get("ongoing_orders")).intValue())
                    .complaintOrders((int) complaintOrders)
                    .registeredPatients((int) registeredPatients)   // ← 强转
                    .registeredEscorts((int) registeredEscorts)     // ← 强转
                    .avgSatisfaction(avgSatisfaction)
                    .complaintRate(complaintRate)
                    .orderTrend(trend)
                    .build();

        } catch (Exception e) {
            log.error("数据统计失败, timeType={}", timeType, e);
            return null;
        }
    }



    /**
     * 实时概况：今日核心指标
     */
    @Override
    public DashboardVO getDashboardOverview() {
        try {
            LocalDateTime todayStart = LocalDateTime.now().with(LocalTime.MIN);
            LocalDateTime todayEnd   = LocalDateTime.now().with(LocalTime.MAX);

            /* 1. 今日订单 */
            Map<String, Object> oMap = serviceOrdersMapper.selectOneByQueryAs(
                    QueryWrapper.create()
                            .select("COUNT(*)   AS today_orders")
                            .select("SUM(CASE WHEN status = 2 THEN 1 ELSE 0 END) AS today_completed")
                            .select("SUM(price) AS today_amount")
                            .from("service_orders")
                            .between("gmt_create", todayStart, todayEnd),
                    Map.class);

            /* 2. 待处理投诉 */
            long pendingComplaints = serviceComplaintMapper.selectCountByQuery(
                    QueryWrapper.create().eq("status", 0));

            /* 3. 待审核陪诊员 */
            long pendingAuditEscorts = serviceEscortMapper.selectCountByQuery(
                    QueryWrapper.create().eq("audit_status", 0));

            /* 4. 在线人数（简单用“今日登录过”代替，可按业务改） */
            long onlinePatients = userPatientMapper.selectCountByQuery(
                    QueryWrapper.create()
                            .between("gmt_modified", todayStart, todayEnd)
                            .eq("status", 1));
            long onlineEscorts  = serviceEscortMapper.selectCountByQuery(
                    QueryWrapper.create()
                            .between("gmt_modified", todayStart, todayEnd)
                            .eq("account_status", 1));

            /* 5. 总用户数 */
            long totalUsers = userPatientMapper.selectCountByQuery(QueryWrapper.create())
                    + serviceEscortMapper.selectCountByQuery(QueryWrapper.create());

            return DashboardVO.builder()
                    .todayNewOrders(((Number) oMap.getOrDefault("today_orders", 0)).intValue())
                    .todayCompletedOrders(((Number) oMap.getOrDefault("today_completed", 0)).intValue())
                    .todayOrderAmount((BigDecimal) oMap.getOrDefault("today_amount", BigDecimal.ZERO))
                    .pendingComplaints((int) pendingComplaints)      // ← 强转
                    .pendingAuditEscorts((int) pendingAuditEscorts)  // ← 强转
                    .onlinePatients((int) onlinePatients)            // ← 强转
                    .onlineEscorts((int) onlineEscorts)              // ← 强转
                    .totalUsers((int) totalUsers)                    // ← 强转
                    .build();

        } catch (Exception e) {
            log.error("实时概况查询失败", e);
            return null;
        }
    }

    private LocalDateTime[] getTimeRange(String timeType) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime;
        LocalDateTime endTime = now;

        switch (timeType) {
            case "day":
                startTime = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
                break;
            case "week":
                startTime = now.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
                        .with(LocalTime.MIN);
                break;
            case "month":
                startTime = now.with(TemporalAdjusters.firstDayOfMonth())
                        .with(LocalTime.MIN);
                break;
            default:
                startTime = now.minusDays(7);
        }

        return new LocalDateTime[]{startTime, endTime};
    }

    private List<OrderTrendVO> getOrderTrendData(String timeType, LocalDateTime startTime, LocalDateTime endTime) {
        List<OrderTrendVO> trendList = new ArrayList<>();

        try {
            QueryWrapper trendQuery = QueryWrapper.create()
                    .select("DATE(gmt_create) as date", "COUNT(*) as count")
                    .between("gmt_create", startTime, endTime)
                    .groupBy("DATE(gmt_create)")
                    .orderBy("DATE(gmt_create)", true);

            // 使用专门的DTO类
            List<OrderTrendDTO> trendData = serviceOrdersMapper.selectListByQueryAs(trendQuery, OrderTrendDTO.class);

            for (OrderTrendDTO data : trendData) {
                OrderTrendVO trend = new OrderTrendVO();

                if (data.getDate() != null && data.getCount() != null) {
                    trend.setPeriod(data.getDate().toString());
                    trend.setOrderCount(data.getCount().intValue());
                    trendList.add(trend);
                }
            }

        } catch (Exception e) {
            log.error("获取订单趋势数据失败", e);
        }

        return trendList;
    }

    private String getOrderStatusDesc(Integer status) {
        if (status == null) {
            return "未知状态";
        }

        return switch (status) {
            case 0 -> "待接单";
            case 1 -> "已接单（进行中）";
            case 2 -> "已完成";
            case 3 -> "已取消";
            case 4 -> "异常/投诉中";
            default -> "未知状态";
        };
    }
}