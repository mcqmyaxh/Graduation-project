package com.example.patient.DTO.VO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class H5LoginVO {
    @Schema(description = "用户/病患主键ID")
    private Integer patientId;
    @Schema(description = "登录账号")
    private String username;
    @Schema(description = "真实姓名")
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
}
