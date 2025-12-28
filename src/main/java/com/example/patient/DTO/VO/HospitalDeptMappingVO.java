package com.example.patient.DTO.VO;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "医院-科室号源映射VO")
public class HospitalDeptMappingVO {

    @Schema(description = "映射ID（更新时必填）")
    private Long mappingId;

    @NotNull(message = "医院ID不能为空")
    @Schema(description = "医院ID", required = true)
    private Integer hospitalId;

    @NotNull(message = "科室ID不能为空")
    @Schema(description = "科室ID", required = true)
    private Integer deptId;

    @NotNull(message = "号源上限不能为空")
    @Schema(description = "号源上限", required = true)
    private Integer maxSource;

    @Schema(description = "已用号源（默认为0）")
    private Integer usedSource = 0;

    @NotNull(message = "状态不能为空")
    @Schema(description = "1启用 0停用", required = true)
    private Integer status;
}
