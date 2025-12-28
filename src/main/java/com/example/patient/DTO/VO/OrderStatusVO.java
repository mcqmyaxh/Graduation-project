package com.example.patient.DTO.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor  // 添加无参构造器
@AllArgsConstructor // 添加全参构造器
public class OrderStatusVO {
    private Long orderId;
    private String orderNo;
    private Integer status;
    private String statusDesc;
    private Integer escortId;
    private String patientNameSnapshot;
    private LocalDateTime appointmentTime;
    private BigDecimal price;
    private LocalDateTime gmtCreate;
}
