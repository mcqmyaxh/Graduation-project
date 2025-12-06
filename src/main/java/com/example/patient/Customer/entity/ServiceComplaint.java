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
 * 投诉管理表 实体类。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("service_complaint")
public class ServiceComplaint implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long complaintId;

    /**
     * 关联订单
     */
    private Long orderId;

    /**
     * 投诉人ID(通常是患者)
     */
    private Integer complainantId;

    /**
     * 被投诉人ID(通常是陪诊员)
     */
    private Integer respondentId;

    /**
     * 投诉内容
     */
    private String content;

    /**
     * 处理状态: 0:待处理 1:已处理
     */
    private Integer status;

    /**
     * 管理员处理结果备注
     */
    private String resultNote;



    /**
     * 处理时间
     */
    private LocalDateTime handledAt;

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
