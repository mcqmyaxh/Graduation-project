package com.example.patient.Customer.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.ManagementAdmin;
import com.example.patient.Customer.mapper.ManagementAdminMapper;
import com.example.patient.Customer.service.ManagementAdminService;
import org.springframework.stereotype.Service;

/**
 * 平台管理员表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class ManagementAdminServiceImpl extends ServiceImpl<ManagementAdminMapper, ManagementAdmin>  implements ManagementAdminService{

}
