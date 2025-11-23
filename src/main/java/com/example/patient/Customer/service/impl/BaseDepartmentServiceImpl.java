package com.example.patient.Customer.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.BaseDepartment;
import com.example.patient.Customer.mapper.BaseDepartmentMapper;
import com.example.patient.Customer.service.BaseDepartmentService;
import org.springframework.stereotype.Service;

/**
 * 科室信息表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class BaseDepartmentServiceImpl extends ServiceImpl<BaseDepartmentMapper, BaseDepartment>  implements BaseDepartmentService{

}
