package com.example.patient.DTO.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "医院科室号源映射详情VO")
public class HospitalDeptMappingDetailVO {

    @Schema(description = "映射ID")
    private Long mappingId;

    @Schema(description = "医院ID")
    private Integer hospitalId;

    @Schema(description = "医院名称")
    private String hospitalName;

    @Schema(description = "科室ID")
    private Integer deptId;

    @Schema(description = "科室名称")
    private String deptName;

    @Schema(description = "号源上限")
    private Integer maxSource;

    @Schema(description = "已用号源")
    private Integer usedSource;

    @Schema(description = "剩余号源")
    private Integer remainingSource;

    @Schema(description = "状态 1启用 0停用")
    private Integer status;

    // 自动计算剩余号源
    public Integer getRemainingSource() {
        return maxSource - usedSource;
    }
}
