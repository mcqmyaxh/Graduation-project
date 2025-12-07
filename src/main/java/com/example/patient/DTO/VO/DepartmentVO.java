package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "科室信息VO")
public class DepartmentVO {

    @Schema(description = "科室ID（更新时必填）")
    private Integer deptId;

    @NotNull(message = "所属医院ID不能为空")
    @Schema(description = "所属医院ID", required = true)
    private Integer hospitalId;

    @NotBlank(message = "科室名称不能为空")
    @Schema(description = "科室名称", required = true)
    private String name;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态 1:启用 0:停用", required = true)
    private Integer status;
}
