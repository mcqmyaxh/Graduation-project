package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单关联的投诉简要信息")
public class ComplaintInfoVO {
    @Schema(description = "投诉ID")
    private Long complaintId;
    @Schema(description = "投诉状态：0待处理 1已处理")
    private Integer status;
    @Schema(description = "投诉内容摘要")
    private String content;
}