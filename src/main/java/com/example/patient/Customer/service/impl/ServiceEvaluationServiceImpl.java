package com.example.patient.Customer.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.ServiceEvaluation;
import com.example.patient.Customer.mapper.ServiceEvaluationMapper;
import com.example.patient.Customer.service.ServiceEvaluationService;
import org.springframework.stereotype.Service;

/**
 * 服务评价表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class ServiceEvaluationServiceImpl extends ServiceImpl<ServiceEvaluationMapper, ServiceEvaluation>  implements ServiceEvaluationService{

}
