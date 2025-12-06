package com.example.patient.Management.service;

import com.example.patient.DTO.Command.UpdateAdminCommand;
import com.mybatisflex.core.service.IService;
import com.example.patient.Management.entity.ManagementAdmin;

import java.util.List;

/**
 * 平台管理员表 服务层。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
public interface ManagementAdminService extends IService<ManagementAdmin> {
    Boolean addAdmin(ManagementAdmin managementAdmin);

    Boolean deleteAdmin(Long adminId);

    Boolean updateAdmin(UpdateAdminCommand command);

    ManagementAdmin getAdmin(ManagementAdmin managementAdmin);

    List<ManagementAdmin> listAdmins(ManagementAdmin managementAdmin);

    ManagementAdmin loginByPhoneAndPassword(String phone, String password);
}
