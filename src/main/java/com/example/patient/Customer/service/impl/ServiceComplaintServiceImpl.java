package com.example.patient.Customer.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.ServiceComplaint;
import com.example.patient.Customer.mapper.ServiceComplaintMapper;
import com.example.patient.Customer.service.ServiceComplaintService;
import org.springframework.stereotype.Service;

/**
 * 投诉管理表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class ServiceComplaintServiceImpl extends ServiceImpl<ServiceComplaintMapper, ServiceComplaint>  implements ServiceComplaintService{

}
