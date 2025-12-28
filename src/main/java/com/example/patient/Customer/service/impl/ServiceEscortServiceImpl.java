package com.example.patient.Customer.service.impl;

import com.example.patient.Customer.entity.ServiceOrders;
import com.example.patient.Customer.mapper.ServiceComplaintMapper;
import com.example.patient.Customer.mapper.ServiceOrdersMapper;
import com.example.patient.DTO.Command.RegisterEscorCommand;
import com.example.patient.DTO.Command.UpdateEscorCommand;
import com.example.patient.DTO.VO.EscorLoginVO;
import com.example.patient.DTO.VO.EscortOrderDetailVO;
import com.example.patient.DTO.VO.H5LoginVO;
import com.example.patient.H5.mapper.UserPatientMapper;
import com.example.patient.Management.entity.BaseDepartment;
import com.example.patient.Management.entity.BaseHospital;
import com.example.patient.Management.mapper.BaseDepartmentMapper;
import com.example.patient.Management.mapper.BaseHospitalMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.ServiceEscort;
import com.example.patient.Customer.mapper.ServiceEscortMapper;
import com.example.patient.Customer.service.ServiceEscortService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.patient.H5.entity.UserPatient;

import com.mybatisflex.core.query.QueryWrapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * 陪诊员信息表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Slf4j
@Service
public class ServiceEscortServiceImpl extends ServiceImpl<ServiceEscortMapper, ServiceEscort>
        implements ServiceEscortService{

    @Resource
    private ServiceEscortMapper serviceEscortMapper;

    @Resource
    private ServiceOrdersMapper serviceOrdersMapper;


    @Resource
    private ServiceComplaintMapper serviceComplaintMapper;


    @Resource
    private UserPatientMapper userPatientMapper;

    @Resource
    private BaseHospitalMapper baseHospitalMapper;

    @Resource
    private BaseDepartmentMapper baseDepartmentMapper;

    @Override
    public Boolean registerEscort(RegisterEscorCommand command) {
        // 检查手机号是否已注册
        QueryWrapper checkWrapper = QueryWrapper.create()
                .eq("username", command.getUsername());
        ServiceEscort existing = mapper.selectOneByQuery(checkWrapper);
        if (existing != null) {
            log.warn("手机号已注册: {}", command.getUsername());
            return false;
        }

        ServiceEscort escort = ServiceEscort.builder()
                .username(command.getUsername())
                .password(command.getPassword()) // 前端应已加密
                .realName(command.getRealName())
                .phone(command.getPhone())
                .idCardImg(command.getIdCardImg())
                .healthCertImg(command.getHealthCertImg())
                .trainingCertImg(command.getTrainingCertImg())
                .auditStatus(0) // 注册后默认为待审核
                .accountStatus(1) // 账号状态正常
                .creditScore(100) // 默认信用分
                .starRating(BigDecimal.valueOf(5.0)) // 默认星级
                .gmtCreate(LocalDateTime.now())
                .gmtModified(LocalDateTime.now())
                .build();

        return mapper.insert(escort) > 0;
    }

    @Override
    public EscorLoginVO loginEscortPhone(String phone, String password) {
        try {
            // 使用StringUtils.isBlank检查空值
            if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
                log.warn("手机号或密码不能为空");
                return null;
            }

            // 使用数据库列名（snake_case）而不是Java字段名（camelCase）
            QueryWrapper wrapper = QueryWrapper.create()
                    .eq("phone", phone)
                    .eq("password", password)
                    .eq("audit_status", 1)  // 数据库列名：audit_status
                    .eq("account_status", 1); // 数据库列名：account_status

            ServiceEscort serviceEscort = serviceEscortMapper.selectOneByQuery(wrapper);
            if (serviceEscort == null) {
                log.warn("手机号登录失败：手机号 {} 不存在或密码错误，或账号未审核/已停用", phone);
                return null;
            }

            // 构建返回的VO对象
            EscorLoginVO escorLoginVO = EscorLoginVO.builder()
                    .escortId(serviceEscort.getEscortId())
                    .username(serviceEscort.getUsername())
                    .realName(serviceEscort.getRealName())
                    .phone(serviceEscort.getPhone())
                    .auditStatus(serviceEscort.getAuditStatus())
                    .accountStatus(serviceEscort.getAccountStatus())
                    .creditScore(serviceEscort.getCreditScore())
                    .starRating(serviceEscort.getStarRating())
                    .build();

            log.info("手机号登录成功：ID {}, 手机号 {}", serviceEscort.getEscortId(), phone);
            return escorLoginVO;

        } catch (Exception e) {
            log.error("手机号登录失败，手机号：{}", phone, e);
            return null;
        }
    }

    @Override
    public EscorLoginVO loginEscortname(String username, String password) {
        try {
            // 使用StringUtils.isBlank检查空值
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                log.warn("用户名或密码不能为空");
                return null;
            }

            // 使用数据库列名（snake_case）而不是Java字段名（camelCase）
            QueryWrapper wrapper = QueryWrapper.create()
                    .eq("username", username)
                    .eq("password", password)
                    .eq("audit_status", 1)  // 数据库列名：audit_status
                    .eq("account_status", 1); // 数据库列名：account_status

            ServiceEscort serviceEscort = serviceEscortMapper.selectOneByQuery(wrapper);
            if (serviceEscort == null) {
                log.warn("账号登录失败：账号 {} 不存在或密码错误，或账号未审核/已停用", username);
                return null;
            }

            // 构建返回的VO对象
            EscorLoginVO escorLoginVO = EscorLoginVO.builder()
                    .escortId(serviceEscort.getEscortId())
                    .username(serviceEscort.getUsername())
                    .realName(serviceEscort.getRealName())
                    .phone(serviceEscort.getPhone())
                    .auditStatus(serviceEscort.getAuditStatus())
                    .accountStatus(serviceEscort.getAccountStatus())
                    .creditScore(serviceEscort.getCreditScore())
                    .starRating(serviceEscort.getStarRating())
                    .build();

            log.info("账号登录成功：ID {}, 账号 {}", serviceEscort.getEscortId(), username);
            return escorLoginVO;

        } catch (Exception e) {
            log.error("账号登录失败，账号：{}", username, e);
            return null;
        }
    }




    @Override
    public Boolean updateEscortInfo(UpdateEscorCommand command) {
        // 先查询是否存在该陪诊员
        ServiceEscort existing = mapper.selectOneById(command.getEscortId());
        if (existing == null) {
            log.warn("陪诊员不存在: {}", command.getEscortId());
            return false;
        }

        // 构建更新对象 - 只更新非空字段
        ServiceEscort escort = ServiceEscort.builder()
                .escortId(command.getEscortId())
                .build();

        // 逐个字段判断并设置
        if (StringUtils.isNotBlank(command.getPassword())) {
            escort.setPassword(command.getPassword());
        }
        if (StringUtils.isNotBlank(command.getRealName())) {
            escort.setRealName(command.getRealName());
        }
        if (StringUtils.isNotBlank(command.getPhone())) {
            escort.setPhone(command.getPhone());
        }
        if (StringUtils.isNotBlank(command.getIdCardImg())) {
            escort.setIdCardImg(command.getIdCardImg());
        }
        if (StringUtils.isNotBlank(command.getHealthCertImg())) {
            escort.setHealthCertImg(command.getHealthCertImg());
        }
        if (StringUtils.isNotBlank(command.getTrainingCertImg())) {
            escort.setTrainingCertImg(command.getTrainingCertImg());
        }

        // 设置更新时间
        escort.setGmtModified(LocalDateTime.now());

        return mapper.update(escort) > 0;
    }

    @Override
    public Page<ServiceEscort> listEscortInfo(Page<ServiceEscort> page) {
        // 无条件分页查询，按创建时间倒序
        QueryWrapper wrapper = QueryWrapper.create()
                .orderBy("gmt_create", false);

        return mapper.paginate(page, wrapper);
    }

    @Override
    public EscorLoginVO getEscortInfo(Integer escortId) {
        ServiceEscort escort = mapper.selectOneById(escortId);
        if (escort == null) {
            log.warn("陪诊员不存在: {}", escortId);
            return null;
        }

        return EscorLoginVO.builder()
                .escortId(escort.getEscortId())
                .username(escort.getUsername())
                .realName(escort.getRealName())
                .phone(escort.getPhone())
                .auditStatus(escort.getAuditStatus())
                .accountStatus(escort.getAccountStatus())
                .creditScore(escort.getCreditScore())
                .starRating(escort.getStarRating())
                .build();
    }

    @Override
    public Page<ServiceOrders> listEscortOrders(Integer escortId, Integer pageNum, Integer pageSize) {
        try {
            if (escortId == null) {
                return new Page<>();
            }

            Page<ServiceOrders> page = new Page<>(pageNum, pageSize);
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .eq(ServiceOrders::getEscortId, escortId)
                    .ne(ServiceOrders::getStatus, 0) // 排除未接单的订单
                    .orderBy(ServiceOrders::getGmtCreate, false);

            return serviceOrdersMapper.paginate(page, queryWrapper);

        } catch (Exception e) {
            log.error("查询陪诊员订单列表失败：陪诊员ID={}", escortId, e);
            return new Page<>();
        }
    }

    /**
     * 陪诊员查看订单大厅（待接单的订单）
     */
    @Override
    public Page<ServiceOrders> listAvailableOrders(Integer pageNum, Integer pageSize, Integer escortId) {
        try {
            Page<ServiceOrders> page = new Page<>(pageNum, pageSize);

            // 查询待接单的订单（status=0），且预约时间晚于当前时间
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .eq(ServiceOrders::getStatus, 0) // 待接单状态
                    .gt(ServiceOrders::getAppointmentTime, LocalDateTime.now()) // 未来时间的订单
                    .orderBy(ServiceOrders::getAppointmentTime, true); // 按预约时间升序

            return serviceOrdersMapper.paginate(page, queryWrapper);

        } catch (Exception e) {
            log.error("查询待接单订单失败", e);
            return new Page<>();
        }
    }

    /**
     * 陪诊员接单
     */
    @Override
    public Boolean acceptOrder(Long orderId, Integer escortId) {
        try {
            if (orderId == null || escortId == null) {
                log.warn("接单失败：订单ID或陪诊员ID为空");
                return false;
            }

            // 1. 验证陪诊员是否存在且状态正常
            ServiceEscort escort = serviceEscortMapper.selectOneById(escortId);
            if (escort == null || escort.getAccountStatus() != 1) {
                log.warn("接单失败：陪诊员不存在或状态不正常，陪诊员ID={}", escortId);
                return false;
            }

            // 2. 验证订单是否存在且为待接单状态
            ServiceOrders order = serviceOrdersMapper.selectOneById(orderId);
            if (order == null) {
                log.warn("接单失败：订单不存在，订单ID={}", orderId);
                return false;
            }

            if (order.getStatus() != 0) {
                log.warn("接单失败：订单状态不是待接单，订单ID={}, 当前状态={}", orderId, order.getStatus());
                return false;
            }

            // 3. 检查陪诊员是否有时间冲突（同一时间段只能接一单）
            LocalDateTime appointmentTime = order.getAppointmentTime();
            if (appointmentTime == null) {
                log.warn("接单失败：订单预约时间为空，订单ID={}", orderId);
                return false;
            }

            Integer estimatedDuration = order.getEstimatedDuration() != null ? order.getEstimatedDuration() : 0;
            LocalDateTime endTime = appointmentTime.plusHours(estimatedDuration);

            /*
            // 方法一：使用原生SQL条件
            String condition = "appointment_time <= ? AND DATE_ADD(appointment_time, INTERVAL estimated_duration HOUR) >= ?";

            QueryWrapper conflictWrapper = QueryWrapper.create()
                    .eq(ServiceOrders::getEscortId, escortId)
                    .eq(ServiceOrders::getStatus, 1) // 进行中的订单
                    .and(w -> w.sql(condition, endTime, appointmentTime));
            */

            // 方法二：或者使用更简单的条件（如果数据库支持时间计算）
             QueryWrapper conflictWrapper = QueryWrapper.create()
                     .eq(ServiceOrders::getEscortId, escortId)
                    .eq(ServiceOrders::getStatus, 1)
                     .le(ServiceOrders::getAppointmentTime, endTime)
                     .ge("appointment_time", appointmentTime.minusHours(estimatedDuration));

            Long conflictCount = serviceOrdersMapper.selectCountByQuery(conflictWrapper);
            if (conflictCount > 0) {
                log.warn("接单失败：陪诊员时间冲突，陪诊员ID={}, 订单预约时间={}", escortId, appointmentTime);
                return false;
            }

            // 4. 更新订单信息（接单）
            ServiceOrders updateOrder = ServiceOrders.builder()
                    .orderId(orderId)
                    .escortId(escortId)
                    .status(1) // 已接单状态
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean updated = serviceOrdersMapper.update(updateOrder) > 0;
            if (updated) {
                log.info("陪诊员接单成功：订单ID={}, 陪诊员ID={}", orderId, escortId);
            }
            return updated;

        } catch (Exception e) {
            log.error("接单失败：订单ID={}, 陪诊员ID={}", orderId, escortId, e);
            return false;
        }
    }

    /**
     * 陪诊员取消接单
     */
    @Override
    public Boolean cancelAcceptOrder(Long orderId, Integer escortId) {
        try {
            if (orderId == null || escortId == null) {
                log.warn("取消接单失败：订单ID或陪诊员ID为空");
                return false;
            }

            // 1. 验证订单是否存在且属于该陪诊员
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .eq(ServiceOrders::getOrderId, orderId)
                    .eq(ServiceOrders::getEscortId, escortId);

            ServiceOrders order = serviceOrdersMapper.selectOneByQuery(queryWrapper);
            if (order == null) {
                log.warn("取消接单失败：订单不存在或不属于该陪诊员，订单ID={}, 陪诊员ID={}", orderId, escortId);
                return false;
            }

            // 2. 只有已接单状态（1）的订单可以取消接单
            if (order.getStatus() != 1) {
                log.warn("取消接单失败：订单状态不允许取消，订单ID={}, 当前状态={}", orderId, order.getStatus());
                return false;
            }

            // 3. 检查是否距离预约时间太近（比如2小时内不能取消）
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime appointmentTime = order.getAppointmentTime();

            if (appointmentTime != null && now.plusHours(2).isAfter(appointmentTime)) {
                log.warn("取消接单失败：距离预约时间不足2小时，不能取消，订单ID={}", orderId);
                return false;
            }

            // 4. 更新订单信息（取消接单，重置为待接单状态）
            ServiceOrders updateOrder = ServiceOrders.builder()
                    .orderId(orderId)
                    .escortId(null) // 清空陪诊员ID
                    .status(0) // 重置为待接单状态
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean updated = serviceOrdersMapper.update(updateOrder) > 0;
            if (updated) {
                // 扣减陪诊员信用分
                ServiceEscort escort = serviceEscortMapper.selectOneById(escortId);
                if (escort != null && escort.getCreditScore() > 0) {
                    int newCreditScore = Math.max(0, escort.getCreditScore() - 5); // 扣5分信用分
                    ServiceEscort updateEscort = ServiceEscort.builder()
                            .escortId(escortId)
                            .creditScore(newCreditScore)
                            .gmtModified(LocalDateTime.now())
                            .build();
                    serviceEscortMapper.update(updateEscort);
                }

                log.info("取消接单成功：订单ID={}, 陪诊员ID={}", orderId, escortId);
            }
            return updated;

        } catch (Exception e) {
            log.error("取消接单失败：订单ID={}, 陪诊员ID={}", orderId, escortId, e);
            return false;
        }
    }

    /**
     * 陪诊员结束订单
     */
    @Override
    public Boolean completeOrder(Long orderId, Integer escortId) {
        try {
            if (orderId == null || escortId == null) {
                log.warn("结束订单失败：订单ID或陪诊员ID为空");
                return false;
            }

            // 1. 验证订单是否存在且属于该陪诊员
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .eq(ServiceOrders::getOrderId, orderId)
                    .eq(ServiceOrders::getEscortId, escortId);

            ServiceOrders order = serviceOrdersMapper.selectOneByQuery(queryWrapper);
            if (order == null) {
                log.warn("结束订单失败：订单不存在或不属于该陪诊员，订单ID={}, 陪诊员ID={}", orderId, escortId);
                return false;
            }

            // 2. 只有已接单状态（1）的订单可以结束
            if (order.getStatus() != 1) {
                log.warn("结束订单失败：订单状态不允许结束，订单ID={}, 当前状态={}", orderId, order.getStatus());
                return false;
            }

            // 3. 检查订单预约时间是否已经过去（不能提前结束）
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime appointmentTime = order.getAppointmentTime();

            if (appointmentTime != null && now.isBefore(appointmentTime)) {
                log.warn("结束订单失败：订单预约时间尚未开始，不能结束，订单ID={}", orderId);
                return false;
            }

            // 4. 更新订单状态为已完成
            ServiceOrders updateOrder = ServiceOrders.builder()
                    .orderId(orderId)
                    .status(2) // 已完成状态
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean updated = serviceOrdersMapper.update(updateOrder) > 0;
            if (updated) {
                log.info("结束订单成功：订单ID={}, 陪诊员ID={}", orderId, escortId);
            }
            return updated;

        } catch (Exception e) {
            log.error("结束订单失败：订单ID={}, 陪诊员ID={}", orderId, escortId, e);
            return false;
        }
    }

    /**
     * 查看被投诉的订单
     */
    @Override
    public Page<ServiceOrders> listComplaintedOrders(Integer escortId, Integer pageNum, Integer pageSize) {
        try {
            if (escortId == null) {
                return new Page<>();
            }

            Page<ServiceOrders> page = new Page<>(pageNum, pageSize);

            // 查询被投诉的订单（状态为4）且属于该陪诊员
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .eq(ServiceOrders::getEscortId, escortId)
                    .eq(ServiceOrders::getStatus, 4) // 投诉中状态
                    .orderBy(ServiceOrders::getGmtModified, false);

            return serviceOrdersMapper.paginate(page, queryWrapper);

        } catch (Exception e) {
            log.error("查询被投诉订单失败：陪诊员ID={}", escortId, e);
            return new Page<>();
        }
    }

    /**
     * 查看陪诊员当前正在进行中的订单
     */
    @Override
    public ServiceOrders getCurrentOrder(Integer escortId) {
        try {
            if (escortId == null) {
                return null;
            }

            // 查询当前进行中的订单（状态为1）且属于该陪诊员
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .eq(ServiceOrders::getEscortId, escortId)
                    .eq(ServiceOrders::getStatus, 1) // 进行中状态
                    .gt(ServiceOrders::getAppointmentTime, LocalDateTime.now().minusDays(1)) // 最近24小时内的订单
                    .orderBy(ServiceOrders::getAppointmentTime, true)
                    .limit(1);

            return serviceOrdersMapper.selectOneByQuery(queryWrapper);

        } catch (Exception e) {
            log.error("查询当前订单失败：陪诊员ID={}", escortId, e);
            return null;
        }
    }

    /**
     * 订单详情：查看患者信息、病情描述、疾病史
     *
     * @param orderId  订单ID
     * @param escortId 陪诊员ID（用于权限验证）
     * @return 订单详情信息
     */
    @Override
    public EscortOrderDetailVO getOrderDetail(Long orderId, Integer escortId) {
        try {
            if (orderId == null || escortId == null) {
                log.warn("查看订单详情失败：订单ID或陪诊员ID为空");
                return null;
            }

            // 1. 验证订单是否存在且属于该陪诊员
            QueryWrapper orderQuery = QueryWrapper.create()
                    .eq(ServiceOrders::getOrderId, orderId)
                    .eq(ServiceOrders::getEscortId, escortId);

            ServiceOrders order = serviceOrdersMapper.selectOneByQuery(orderQuery);
            if (order == null) {
                log.warn("查看订单详情失败：订单不存在或不属于该陪诊员，订单ID={}, 陪诊员ID={}", orderId, escortId);
                return null;
            }

            // 2. 获取患者信息
            UserPatient patient = userPatientMapper.selectOneById(order.getPatientId());
            if (patient == null) {
                log.warn("查看订单详情失败：患者信息不存在，患者ID={}", order.getPatientId());
                return null;
            }

            // 3. 获取陪诊员信息
            ServiceEscort escort = serviceEscortMapper.selectOneById(escortId);
            if (escort == null) {
                log.warn("查看订单详情失败：陪诊员信息不存在，陪诊员ID={}", escortId);
                return null;
            }

            // 4. 获取医院信息
            BaseHospital hospital = null;
            if (order.getHospitalId() != null) {
                hospital = baseHospitalMapper.selectOneById(order.getHospitalId());
            }

            // 5. 获取科室信息
            BaseDepartment department = null;
            if (order.getDeptId() != null) {
                department = baseDepartmentMapper.selectOneById(order.getDeptId());
            }

            // 6. 构建状态描述
            String statusDesc = getOrderStatusDesc(order.getStatus());

            // 7. 构建性别描述
            String genderDesc = getGenderDesc(patient.getGender());

            // 8. 构建返回的VO对象
            EscortOrderDetailVO detailVO = EscortOrderDetailVO.builder()
                    .orderId(order.getOrderId())
                    .orderNo(order.getOrderNo())
                    .status(order.getStatus())
                    .statusDesc(statusDesc)
                    .patientId(patient.getPatientId())
                    .patientName(patient.getRealName())
                    .patientAge(patient.getAge())
                    .patientGender(genderDesc)
                    .patientPhone(patient.getPhone())
                    .patientMedicalHistory(patient.getMedicalHistory())
                    .illnessDesc(order.getIllnessDesc())
                    .escortId(escort.getEscortId())
                    .escortName(escort.getRealName())
                    .escortPhone(escort.getPhone())
                    .hospitalId(order.getHospitalId())
                    .hospitalName(hospital != null ? hospital.getName() : "未知医院")
                    .deptId(order.getDeptId())
                    .deptName(department != null ? department.getName() : "未知科室")
                    .appointmentTime(order.getAppointmentTime())
                    .estimatedDuration(order.getEstimatedDuration())
                    .price(order.getPrice())
                    .gmtCreate(order.getGmtCreate())
                    .gmtModified(order.getGmtModified())
                    .build();

            log.info("查看订单详情成功：订单ID={}, 陪诊员ID={}", orderId, escortId);
            return detailVO;

        } catch (Exception e) {
            log.error("查看订单详情失败：订单ID={}, 陪诊员ID={}", orderId, escortId, e);
            return null;
        }
    }

    /**
     * 获取订单状态描述
     */
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

    /**
     * 获取性别描述
     */
    private String getGenderDesc(Integer gender) {
        if (gender == null) {
            return "未知";
        }

        return switch (gender) {
            case 1 -> "男";
            case 2 -> "女";
            default -> "未知";
        };
    }

}
