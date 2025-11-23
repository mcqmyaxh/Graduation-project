package com.example.patient.Customer.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.UserPatient;
import com.example.patient.Customer.mapper.UserPatientMapper;
import com.example.patient.Customer.service.UserPatientService;
import org.springframework.stereotype.Service;

/**
 * 患者/家属信息表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class UserPatientServiceImpl extends ServiceImpl<UserPatientMapper, UserPatient>  implements UserPatientService{

}
