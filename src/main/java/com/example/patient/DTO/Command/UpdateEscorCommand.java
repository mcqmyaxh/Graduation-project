package com.example.patient.DTO.Command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEscorCommand {

    @NotNull(message = "陪诊员ID不能为空")
    private Integer escortId;

    @Schema(description = "陪诊员登录密码")
    private String password;

    @Schema(description = "陪诊员真实姓名")
    private String realName;

    @Schema(description = "陪诊员的联系电话")
    private String phone;

    @Schema(description = "陪诊员的身份证照片")
    private String idCardImg;
    @Schema(description = "陪诊员的健康证照片")
    private String healthCertImg;
    @Schema(description = "陪诊员的培训证书")
    private String trainingCertImg;
}
