package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Builder
@Schema(description = "投诉信息VO")
public class ComplaintVO {
    @Schema(description = "投诉ID")
    private Long complaintId;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "投诉人ID")
    private Integer complainantId;

    @Schema(description = "投诉人姓名")
    private String complainantName;

    @Schema(description = "被投诉人ID")
    private Integer respondentId;

    @Schema(description = "被投诉人姓名")
    private String respondentName;

    @Schema(description = "投诉内容")
    private String content;

    @Schema(description = "处理状态: 0待处理 1已处理")
    private Integer status;

    @Schema(description = "处理结果备注")
    private String resultNote;

    @Schema(description = "处理时间")
    private LocalDateTime handledAt;

    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;
}
