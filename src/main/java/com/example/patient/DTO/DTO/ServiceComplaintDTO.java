package com.example.patient.DTO.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class ServiceComplaintDTO {
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @NotNull(message = "投诉人ID不能为空")
    private Integer complainantId;

    @NotNull(message = "被投诉人ID不能为空")
    private Integer respondentId;

    @NotBlank(message = "投诉内容不能为空")
    private String content;
}
