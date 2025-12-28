package com.example.patient.DTO.VO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "陪诊员查看订单详情VO")
public class EscortOrderDetailVO {
    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "订单状态")
    private Integer status;

    @Schema(description = "订单状态描述")
    private String statusDesc;

    @Schema(description = "患者ID")
    private Integer patientId;

    @Schema(description = "患者姓名")
    private String patientName;

    @Schema(description = "患者年龄")
    private Integer patientAge;

    @Schema(description = "患者性别")
    private String patientGender;

    @Schema(description = "患者手机号")
    private String patientPhone;

    @Schema(description = "患者疾病史")
    private String patientMedicalHistory;

    @Schema(description = "本次病情描述")
    private String illnessDesc;

    @Schema(description = "陪诊员ID")
    private Integer escortId;

    @Schema(description = "陪诊员姓名")
    private String escortName;

    @Schema(description = "陪诊员手机号")
    private String escortPhone;

    @Schema(description = "医院ID")
    private Integer hospitalId;

    @Schema(description = "医院名称")
    private String hospitalName;

    @Schema(description = "科室ID")
    private Integer deptId;

    @Schema(description = "科室名称")
    private String deptName;

    @Schema(description = "预约时间")
    private LocalDateTime appointmentTime;

    @Schema(description = "预估时长(小时)")
    private Integer estimatedDuration;

    @Schema(description = "订单金额")
    private BigDecimal price;

    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;

    @Schema(description = "更新时间")
    private LocalDateTime gmtModified;

}
