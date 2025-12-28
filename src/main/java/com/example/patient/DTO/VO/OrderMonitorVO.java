package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "订单监控VO")
public class OrderMonitorVO {
    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "患者ID")
    private Integer patientId;

    @Schema(description = "患者姓名")
    private String patientName;

    @Schema(description = "陪诊员ID")
    private Integer escortId;

    @Schema(description = "陪诊员姓名")
    private String escortName;

    @Schema(description = "状态: 0待接单 1已接单 2已完成 3已取消 4投诉中")
    private Integer status;

    @Schema(description = "状态描述")
    private String statusDesc;

    @Schema(description = "医院ID")
    private Integer hospitalId;

    @Schema(description = "医院名称")
    private String hospitalName;

    @Schema(description = "科室名称")
    private String deptName;

    @Schema(description = "预约时间")
    private LocalDateTime appointmentTime;

    @Schema(description = "订单金额")
    private BigDecimal price;

    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;

    @Schema(description = "是否异常")
    private Boolean isAbnormal;
}
