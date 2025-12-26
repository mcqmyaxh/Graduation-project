package com.example.patient.DTO.VO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscorLoginVO {

    private Integer escortId;
    private String username;      // 手机号
    private String realName;
    private String phone;
    private Integer auditStatus;  // 认证状态
    private Integer accountStatus; // 账号状态
    private Integer creditScore;   // 信用积分
    private BigDecimal starRating; // 综合星级
}
