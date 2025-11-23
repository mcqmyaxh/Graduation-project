package com.example.patient.Customer.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 陪诊订单表 实体类。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("service_orders")
public class ServiceOrders implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @Id(keyType = KeyType.Auto)
    private Long orderId;

    /**
     * 订单编号(唯一业务号)
     */
    private String orderNo;

    /**
     * 下单患者ID
     */
    private Integer patientId;

    /**
     * 下单时患者姓名快照
     */
    private String patientNameSnapshot;

    /**
     * 病情描述
     */
    private String illnessDesc;

    /**
     * 预约医院
     */
    private Integer hospitalId;

    /**
     * 预约科室
     */
    private Integer deptId;

    /**
     * 预约陪诊时间
     */
    private LocalDateTime appointmentTime;

    /**
     * 预估时长(小时)
     */
    private Integer estimatedDuration;

    /**
     * 接单陪诊员ID(未接单为空)
     */
    private Integer escortId;

    /**
     * 状态: 0:待接单 1:已接单(进行中) 2:已完成 3:已取消 4:异常/投诉中
     */
    private Integer status;

    /**
     * 订单金额
     */
    private BigDecimal price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
