package com.example.patient.Management.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Management.entity.BaseDepartment;
import com.example.patient.Management.mapper.BaseDepartmentMapper;
import com.example.patient.Management.service.BaseDepartmentService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.List;

/**
 * 科室信息表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class BaseDepartmentServiceImpl extends ServiceImpl<BaseDepartmentMapper, BaseDepartment>  implements BaseDepartmentService{

    /**
     * 根据医院ID获取科室列表
     *
     * @param hospitalId
     */

    @Override
    public List<BaseDepartment> listByHospitalDepartmentId(Integer hospitalId) {
        return queryChain()
                .where(BaseDepartment::getHospitalId).eq(hospitalId)
                .eq(BaseDepartment::getStatus, 1) // 仅查询启用的科室
                .list();
    }

    @Override
    public BaseDepartment getDepartmentById(Integer deptId) {
        return this.getById(deptId);
    }

    // 停用/启用科室
    @Override
    public boolean updateHospitalDepartmentStatus(Integer deptId, Integer status) {
        return updateChain()
                .set(BaseDepartment::getStatus, status)
                .where(BaseDepartment::getDeptId).eq(deptId)
                .update();
    }
}
