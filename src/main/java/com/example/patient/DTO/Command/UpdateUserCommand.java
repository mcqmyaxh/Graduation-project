package com.example.patient.DTO.Command;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.math.BigInteger;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserCommand {

    @Schema(description = "用户ID")
    private Integer patientId;

    @Schema(description = "登录账号/手机号")
    private String username;

    @Schema(description = "用户密码")
    private String password;

    @Schema(description = "用户真实姓名")
    private String realName;

    @Schema(description = "性别")
    private Integer gender;


    @Schema(description = "年龄")
    private Integer age;


    @Schema(description = "病史描述")
    private String medicalHistory;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "状态 1:启用 0:禁用(黑名单)")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;

    @Schema(description = "更新时间")
    private LocalDateTime gmtModified;
}
