package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "评价信息")
public class EvaluationInfoVO {
    @Schema(description = "评价ID")
    private Long evaluationId;
    @Schema(description = "评分（1-5）")
    private BigDecimal score;
    @Schema(description = "评价内容")
    private String content;
    @Schema(description = "评价时间")
    private LocalDateTime gmtCreate;


}
