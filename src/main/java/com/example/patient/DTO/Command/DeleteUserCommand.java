package com.example.patient.DTO.Command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteUserCommand {
    @Schema(description = "用户ID")
    private Integer patientId;

    @Schema(description = "操作员ID")
    private Integer operatorId;

    @Schema(description = "删除原因")
    private String reason;
}
