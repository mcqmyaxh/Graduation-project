package com.example.patient.Customer.service.impl;

import com.example.patient.Customer.entity.ServiceComplaint;
import com.example.patient.Customer.entity.ServiceEscort;
import com.example.patient.Customer.entity.ServiceEvaluation;
import com.example.patient.Customer.mapper.ServiceEvaluationMapper;
import com.example.patient.DTO.VO.*;
import com.example.patient.H5.entity.UserPatient;
import com.example.patient.Management.entity.BaseDepartment;
import com.example.patient.Management.entity.BaseHospital;
import com.example.patient.Management.mapper.ManagementAdminMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.ServiceOrders;
import com.example.patient.Customer.mapper.ServiceOrdersMapper;
import com.example.patient.Customer.service.ServiceOrdersService;
import org.springframework.stereotype.Service;
import com.example.patient.Customer.mapper.ServiceComplaintMapper;
import com.example.patient.H5.mapper.UserPatientMapper;
import com.example.patient.Management.mapper.BaseDepartmentMapper;
import com.example.patient.Management.mapper.BaseHospitalMapper;
import com.example.patient.Customer.mapper.ServiceEscortMapper;
import java.math.BigDecimal;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import com.mybatisflex.core.query.QueryWrapper;

/**
 * 陪诊订单表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Slf4j
@Service
public class ServiceOrdersServiceImpl extends ServiceImpl<ServiceOrdersMapper, ServiceOrders>
        implements ServiceOrdersService{

    @Resource
    private UserPatientMapper userPatientMapper;

    @Resource
    private ServiceEscortMapper serviceEscortMapper;

    @Resource
    private ServiceOrdersMapper serviceOrdersMapper;

    @Resource
    private ServiceEvaluationMapper serviceEvaluationMapper;

    @Resource
    private ServiceComplaintMapper serviceComplaintMapper;

    @Resource
    private BaseHospitalMapper baseHospitalMapper;

    @Resource
    private BaseDepartmentMapper baseDepartmentMapper;

    /**
     * 查看全平台订单（可筛选状态）
     *
     * @param pageNum
     * @param pageSize
     * @param status
     */
    @Override
    public Page<OrderMonitorVO> monitorAllOrders(Integer pageNum, Integer pageSize, Integer status) {
        try {
            Page<OrderMonitorVO> page = new Page<>(pageNum, pageSize);

            QueryWrapper queryWrapper = QueryWrapper.create()
                    .select("so.*")
                    .select("up.real_name AS patient_name")
                    .select("se.real_name AS escort_name")
                    .select("bh.name AS hospital_name")
                    .select("bd.name AS dept_name")
                    .from("service_orders so")
                    .leftJoin("user_patient up").on("so.patient_id = up.patient_id")
                    .leftJoin("service_escort se").on("so.escort_id = se.escort_id")
                    .leftJoin("base_hospital bh").on("so.hospital_id = bh.hospital_id")
                    .leftJoin("base_department bd").on("so.dept_id = bd.dept_id");

            if (status != null) {
                queryWrapper.where("so.status = ?", status);
            }

            queryWrapper.orderBy("so.gmt_create", false);

            return serviceOrdersMapper.paginateAs(page, queryWrapper, OrderMonitorVO.class);

        } catch (Exception e) {
            log.error("监控订单失败", e);
            return new Page<>();
        }
    }

    /**
     * 查看异常订单（投诉中的订单）
     *
     * @param pageNum
     * @param pageSize
     */
    @Override
    public Page<OrderMonitorVO> listComplaintOrders(Integer pageNum, Integer pageSize) {
        try {
            Page<OrderMonitorVO> page = new Page<>(pageNum, pageSize);

            QueryWrapper queryWrapper = QueryWrapper.create()
                    .select("so.*")
                    .select("up.real_name AS patient_name")
                    .select("se.real_name AS escort_name")
                    .select("bh.name AS hospital_name")
                    .select("bd.name AS dept_name")
                    .from("service_orders so")
                    .leftJoin("user_patient up").on("so.patient_id = up.patient_id")
                    .leftJoin("service_escort se").on("so.escort_id = se.escort_id")
                    .leftJoin("base_hospital bh").on("so.hospital_id = bh.hospital_id")
                    .leftJoin("base_department bd").on("so.dept_id = bd.dept_id")
                    .where("so.status = 4"); // 投诉中状态

            queryWrapper.orderBy("so.gmt_modified", false);

            return serviceOrdersMapper.paginateAs(page, queryWrapper, OrderMonitorVO.class);

        } catch (Exception e) {
            log.error("查询投诉订单失败", e);
            return new Page<>();
        }
    }

    /**
     * 查看订单详情（管理员版）
     *
     * @param orderId
     */
    @Override
    public AdminOrderDetailVO getAdminOrderDetail(Long orderId) {
        try {
            if (orderId == null) {
                return null;
            }

            // 查询订单基本信息
            ServiceOrders order = serviceOrdersMapper.selectOneById(orderId);
            if (order == null) {
                return null;
            }

            // 查询患者信息
            UserPatient patient = userPatientMapper.selectOneById(order.getPatientId());
            UserSimpleVO patientInfo = null;
            if (patient != null) {
                patientInfo = UserSimpleVO.builder()
                        .patientId(patient.getPatientId())
                        .realName(patient.getRealName())
                        .phone(patient.getPhone())
                        .build();
            }

            // 查询陪诊员信息
            EscortSimpleVO escortInfo = null;
            if (order.getEscortId() != null) {
                ServiceEscort escort = serviceEscortMapper.selectOneById(order.getEscortId());
                if (escort != null) {
                    escortInfo = EscortSimpleVO.builder()
                            .escortId(escort.getEscortId())
                            .realName(escort.getRealName())
                            .phone(escort.getPhone())
                            .build();
                }
            }

            // 查询医院信息
            HospitalSimpleVO hospitalInfo = null;
            if (order.getHospitalId() != null) {
                BaseHospital hospital = baseHospitalMapper.selectOneById(order.getHospitalId());
                if (hospital != null) {
                    hospitalInfo = HospitalSimpleVO.builder()
                            .hospitalId(hospital.getHospitalId())
                            .name(hospital.getName())
                            .address(hospital.getAddress())
                            .build();
                }
            }

            // 查询科室信息
            DepartmentSimpleVO deptInfo = null;
            if (order.getDeptId() != null) {
                BaseDepartment department = baseDepartmentMapper.selectOneById(order.getDeptId());
                if (department != null) {
                    deptInfo = DepartmentSimpleVO.builder()
                            .deptId(department.getDeptId())
                            .name(department.getName())
                            .build();
                }
            }

            // 查询评价信息
            EvaluationInfoVO evaluationInfo = null;
            QueryWrapper evalQuery = QueryWrapper.create()
                    .eq(ServiceEvaluation::getOrderId, orderId);
            ServiceEvaluation evaluation = serviceEvaluationMapper.selectOneByQuery(evalQuery);
            if (evaluation != null) {
                evaluationInfo = EvaluationInfoVO.builder()
                        .evaluationId(evaluation.getEvalId())
                        .score(BigDecimal.valueOf(evaluation.getScore()))  // ← 类型转换
                        .content(evaluation.getComment())
                        .gmtCreate(evaluation.getGmtCreate())
                        .build();
            }

            // 查询投诉信息
            ComplaintInfoVO complaintInfo = null;
            QueryWrapper complaintQuery = QueryWrapper.create()
                    .eq(ServiceComplaint::getOrderId, orderId);
            ServiceComplaint complaint = serviceComplaintMapper.selectOneByQuery(complaintQuery);
            if (complaint != null) {
                complaintInfo = ComplaintInfoVO.builder()
                        .content(complaint.getContent())
                        .status(complaint.getStatus())
                        .build();
            }

            // 构建状态描述
            String statusDesc = getOrderStatusDesc(order.getStatus());

            // 构建返回对象
            AdminOrderDetailVO detailVO = AdminOrderDetailVO.builder()
                    .orderId(order.getOrderId())
                    .orderNo(order.getOrderNo())
                    .status(order.getStatus())
                    .statusDesc(statusDesc)
                    .patientInfo(patientInfo)
                    .escortInfo(escortInfo)
                    .hospitalInfo(hospitalInfo)
                    .deptInfo(deptInfo)
                    .illnessDesc(order.getIllnessDesc())
                    .appointmentTime(order.getAppointmentTime())
                    .estimatedDuration(order.getEstimatedDuration())
                    .price(order.getPrice())
                    .evaluationInfo(evaluationInfo)
                    .complaintInfo(complaintInfo)
                    .gmtCreate(order.getGmtCreate())
                    .build();

            return detailVO;

        } catch (Exception e) {
            log.error("查询订单详情失败：订单ID={}", orderId, e);
            return null;
        }
    }

    /**
     * 把数字状态翻译成中文
     */
    private String getOrderStatusDesc(Integer status) {
        if (status == null) return "";
        switch (status) {
            case 0:
                return "待接单";
            case 1:
                return "已接单";
            case 2:
                return "已完成";
            case 3:
                return "已取消";
            case 4:
                return "投诉中";
            default:
                return "未知";
        }
    }
}
