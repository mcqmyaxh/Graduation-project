package com.example.patient.Management.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Management.entity.BaseDepartment;
import com.example.patient.Management.mapper.BaseDepartmentMapper;
import com.example.patient.Management.service.BaseDepartmentService;
import org.springframework.stereotype.Service;
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
    public List<BaseDepartment> listByHospitalId(Integer hospitalId) {
        return null;
    }
}
