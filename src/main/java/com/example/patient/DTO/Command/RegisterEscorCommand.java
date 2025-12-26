package com.example.patient.DTO.Command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterEscorCommand {
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String username; // 登录账号/手机号

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @NotBlank(message = "联系电话不能为空")
    private String phone;

    private String idCardImg;        // 身份证照片路径（可选，前端上传后回传路径）
    private String healthCertImg;    // 健康证照片路径
    private String trainingCertImg;  // 培训证书路径
}
