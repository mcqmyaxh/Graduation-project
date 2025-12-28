package com.example.patient.DTO.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UpdateOrderDTO {
    private String illnessDesc;
    private LocalDateTime appointmentTime;
    private Integer estimatedDuration;
}