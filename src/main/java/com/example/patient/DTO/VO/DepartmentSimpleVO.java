package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "科室简要信息")
public class DepartmentSimpleVO {
    @Schema(description = "科室ID")
    private Integer deptId;
    @Schema(description = "科室名称")
    private String name;
}
