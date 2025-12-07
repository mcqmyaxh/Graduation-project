package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
@Schema(description = "医院信息VO")
public class HospitalVO {

    @Schema(description = "医院ID（更新时必填）")
    private Integer hospitalId;

    @NotBlank(message = "医院名称不能为空")
    @Schema(description = "医院名称", required = true)
    private String name;

    @Schema(description = "医院地址")
    private String address;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态 1:启用 0:停用", required = true)
    private Integer status;
}
