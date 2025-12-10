package com.example.patient.Management.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.example.patient.Management.entity.BaseHospitalDeptMapping;

import java.util.List;

/**
 * 医院-科室号源映射表 服务层。
 *
 * @author MECHREV
 * @since 2025-12-07
 */
public interface BaseHospitalDeptMappingService extends IService<BaseHospitalDeptMapping> {
    /**
     * 配置科室号源（如果存在则更新上限，不存在则新增）
     * @param mapping 包含医院ID、科室ID、号源上限
     * @return 是否成功
     */
    boolean configDepartmentSlots(BaseHospitalDeptMapping mapping);

    /**
     * 获取指定医院指定科室的配置详情
     */
    BaseHospitalDeptMapping getDepartmentMapping(Integer hospitalId, Integer deptId);

    /**
     * 根据医院ID查询该医院下所有科室的号源配置
     */
    List<BaseHospitalDeptMapping> listByHospitalId(Integer hospitalId);

    /**
     * 分页查询号源配置（可选）
     */
    Page<BaseHospitalDeptMapping> pageMapping(Integer pageNumber, Integer pageSize, Integer hospitalId);
}
