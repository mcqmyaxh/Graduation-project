package com.example.patient.Management.service;

import com.mybatisflex.core.service.IService;
import com.example.patient.Management.entity.BaseDepartment;
import java.util.List;
/**
 * 科室信息表 服务层。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
public interface BaseDepartmentService extends IService<BaseDepartment> {
    /**
     * 根据医院ID获取科室列表
     */
    List<BaseDepartment> listByHospitalDepartmentId(Integer hospitalId);

    BaseDepartment getDepartmentById(Integer deptId);

    /**
     * 修改科室状态
     * @param deptId 科室ID
     * @param status 状态 1:启用 0:停用
     */
    boolean updateHospitalDepartmentStatus(Integer deptId, Integer status);

}
