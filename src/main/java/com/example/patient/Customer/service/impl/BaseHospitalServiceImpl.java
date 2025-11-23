package com.example.patient.Customer.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.BaseHospital;
import com.example.patient.Customer.mapper.BaseHospitalMapper;
import com.example.patient.Customer.service.BaseHospitalService;
import org.springframework.stereotype.Service;

/**
 * 医院信息表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class BaseHospitalServiceImpl extends ServiceImpl<BaseHospitalMapper, BaseHospital>  implements BaseHospitalService{

}
