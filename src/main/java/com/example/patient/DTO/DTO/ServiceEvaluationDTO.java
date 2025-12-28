package com.example.patient.DTO.DTO;

import lombok.Data;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class ServiceEvaluationDTO {
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @NotNull(message = "患者ID不能为空")
    private Integer patientId;

    @NotNull(message = "陪诊员ID不能为空")
    private Integer escortId;

    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分最低为1星")
    @Max(value = 5, message = "评分最高为5星")
    private Integer score;

    private String comment;
}
