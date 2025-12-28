package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "数据统计VO")
public class StatisticsVO {
    @Schema(description = "统计时间段类型: day/week/month")
    private String timeType;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "总订单数")
    private Integer totalOrders;

    @Schema(description = "订单总金额")
    private BigDecimal totalAmount;

    @Schema(description = "完成订单数")
    private Integer completedOrders;

    @Schema(description = "进行中订单数")
    private Integer ongoingOrders;

    @Schema(description = "投诉订单数")
    private Integer complaintOrders;

    @Schema(description = "注册患者数")
    private Integer registeredPatients;

    @Schema(description = "注册陪诊员数")
    private Integer registeredEscorts;

    @Schema(description = "平均满意度")
    private BigDecimal avgSatisfaction;

    @Schema(description = "投诉率")
    private BigDecimal complaintRate;

    @Schema(description = "订单趋势数据")
    private List<OrderTrendVO> orderTrend;
}

