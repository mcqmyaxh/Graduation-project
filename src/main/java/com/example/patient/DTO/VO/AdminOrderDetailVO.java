package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "管理员订单详情VO")
public class AdminOrderDetailVO {
    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "状态描述")
    private String statusDesc;

    @Schema(description = "患者信息")
    private UserSimpleVO patientInfo;

    @Schema(description = "陪诊员信息")
    private EscortSimpleVO escortInfo;

    @Schema(description = "医院信息")
    private HospitalSimpleVO hospitalInfo;

    @Schema(description = "科室信息")
    private DepartmentSimpleVO deptInfo;

    @Schema(description = "病情描述")
    private String illnessDesc;

    @Schema(description = "预约时间")
    private LocalDateTime appointmentTime;

    @Schema(description = "预估时长(小时)")
    private Integer estimatedDuration;

    @Schema(description = "订单金额")
    private BigDecimal price;

    @Schema(description = "评价信息")
    private EvaluationInfoVO evaluationInfo;

    @Schema(description = "投诉信息")
    private ComplaintInfoVO complaintInfo;

    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;
}
