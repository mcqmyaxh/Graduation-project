package com.example.patient.DTO.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateOrderDTO {
    @NotNull(message = "患者ID不能为空")
    private Integer patientId;

    @NotNull(message = "医院ID不能为空")
    private Integer hospitalId;

    @NotNull(message = "科室ID不能为空")
    private Integer deptId;

    @NotNull(message = "预约时间不能为空")
    private LocalDateTime appointmentTime;

    @NotNull(message = "预估时长不能为空")
    private Integer estimatedDuration;

    private String illnessDesc;

    @NotNull(message = "订单金额不能为空")
    private BigDecimal price;
}
