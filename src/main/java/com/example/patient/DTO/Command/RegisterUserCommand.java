package com.example.patient.DTO.Command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegisterUserCommand {
    @Schema(description = "用户密码")
    private String password;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "性别 0:未知 1:男 2:女")
    private Integer gender;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "病史描述")
    private String medicalHistory;

    @Schema(description = "联系电话")
    private String phone;
}
