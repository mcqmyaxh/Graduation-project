package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "仪表板概览VO")
public class DashboardVO {
    @Schema(description = "今日新增订单")
    private Integer todayNewOrders;

    @Schema(description = "今日完成订单")
    private Integer todayCompletedOrders;

    @Schema(description = "今日订单金额")
    private BigDecimal todayOrderAmount;

    @Schema(description = "待处理投诉")
    private Integer pendingComplaints;

    @Schema(description = "待审核陪诊员")
    private Integer pendingAuditEscorts;

    @Schema(description = "在线患者数")
    private Integer onlinePatients;

    @Schema(description = "在线陪诊员数")
    private Integer onlineEscorts;

    @Schema(description = "系统总用户数")
    private Integer totalUsers;
}
