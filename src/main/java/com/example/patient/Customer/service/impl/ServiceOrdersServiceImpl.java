package com.example.patient.Customer.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.ServiceOrders;
import com.example.patient.Customer.mapper.ServiceOrdersMapper;
import com.example.patient.Customer.service.ServiceOrdersService;
import org.springframework.stereotype.Service;

/**
 * 陪诊订单表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class ServiceOrdersServiceImpl extends ServiceImpl<ServiceOrdersMapper, ServiceOrders>  implements ServiceOrdersService{

}
