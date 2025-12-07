package com.example.patient.Management.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 医院-科室号源映射表 实体类。
 *
 * @author MECHREV
 * @since 2025-12-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("base_hospital_dept_mapping")
public class BaseHospitalDeptMapping implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 映射ID
     */
    @Id(keyType = KeyType.Auto)
    private Long mappingId;

    /**
     * 医院ID
     */
    private Integer hospitalId;

    /**
     * 科室ID
     */
    private Integer deptId;

    /**
     * 号源上限
     */
    private Integer maxSource;

    /**
     * 已用号源
     */
    private Integer usedSource;

    /**
     * 1启用 0停用
     */
    private Integer status;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

}
