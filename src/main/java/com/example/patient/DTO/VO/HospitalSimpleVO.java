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
@Schema(description = "医院简要信息")
public class HospitalSimpleVO {
    @Schema(description = "医院ID")
    private Integer hospitalId;
    @Schema(description = "医院名称")
    private String name;

    @Schema(description = "医院地址")
    private String address;
}
