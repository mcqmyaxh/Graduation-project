package com.example.patient.DTO.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
@Data
public class OrderAcceptDTO {
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @NotNull(message = "陪诊员ID不能为空")
    private Integer escortId;
}
