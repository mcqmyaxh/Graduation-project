package com.example.patient.DTO.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class OrderTrendDTO {
    private LocalDate date;
    private Long count;  // 注意：COUNT(*)返回的是Long类型
}
