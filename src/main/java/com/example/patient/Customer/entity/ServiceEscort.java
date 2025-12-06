package com.example.patient.Customer.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 陪诊员信息表 实体类。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("service_escort")
public class ServiceEscort implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 陪诊员ID
     */
    @Id(keyType = KeyType.Auto)
    private Integer escortId;

    /**
     * 登录账号/手机号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 身份证照片路径
     */
    private String idCardImg;

    /**
     * 健康证照片路径
     */
    private String healthCertImg;

    /**
     * 培训证书路径
     */
    private String trainingCertImg;

    /**
     * 认证状态 0:待审核 1:审核通过 2:审核驳回
     */
    private Integer auditStatus;

    /**
     * 账号状态 1:正常 2:暂停接单 3:封号(黑名单)
     */
    private Integer accountStatus;

    /**
     * 信用积分(默认100，差评扣分)
     */
    private Integer creditScore;

    /**
     * 综合星级(0.0-5.0)
     */
    private BigDecimal starRating;



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
