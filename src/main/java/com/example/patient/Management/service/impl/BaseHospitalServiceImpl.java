package com.example.patient.Management.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Management.entity.BaseHospital;
import com.example.patient.Management.mapper.BaseHospitalMapper;
import com.example.patient.Management.service.BaseHospitalService;
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
