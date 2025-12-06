package com.example.patient.Customer.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务评价表 实体类。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("service_evaluation")
public class ServiceEvaluation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long evalId;

    /**
     * 关联订单
     */
    private Long orderId;

    /**
     * 评价人
     */
    private Integer patientId;

    /**
     * 被评价陪诊员
     */
    private Integer escortId;

    /**
     * 评分 1-5星
     */
    private Integer score;

    /**
     * 评价内容
     */
    private String comment;



    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime gmtModified;

}
