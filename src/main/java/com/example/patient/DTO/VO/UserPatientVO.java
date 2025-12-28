package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "用户/患者信息VO（管理员用）")
public class UserPatientVO {
    @Schema(description = "患者ID")
    private Integer patientId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "疾病史")
    private String medicalHistory;

    @Schema(description = "状态: 1启用 0禁用")
    private Integer status;

    @Schema(description = "订单总数")
    private Integer orderCount;

    @Schema(description = "投诉次数")
    private Integer complaintCount;

    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;
}
