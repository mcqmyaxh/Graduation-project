package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "陪诊员信息VO（管理员用）")
public class EscortInfoVO {
    @Schema(description = "陪诊员ID")
    private Integer escortId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "认证状态: 0待审核 1审核通过 2审核驳回")
    private Integer auditStatus;

    @Schema(description = "账号状态: 1正常 2暂停接单 3封号(黑名单)")
    private Integer accountStatus;

    @Schema(description = "信用积分")
    private Integer creditScore;

    @Schema(description = "综合星级")
    private BigDecimal starRating;

    @Schema(description = "接单总数")
    private Integer orderCount;

    @Schema(description = "完成订单数")
    private Integer completedCount;

    @Schema(description = "被投诉次数")
    private Integer complaintCount;

    @Schema(description = "平均评分")
    private BigDecimal avgScore;

    @Schema(description = "注册时间")
    private LocalDateTime gmtCreate;
}
