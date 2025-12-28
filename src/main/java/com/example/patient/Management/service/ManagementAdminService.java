package com.example.patient.Management.service;

import com.example.patient.DTO.Command.UpdateAdminCommand;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.example.patient.Management.entity.ManagementAdmin;
import com.example.patient.DTO.VO.*;
import java.time.LocalDateTime;
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

    /**
     * 启用/禁用患者账号
     */
    Boolean togglePatientStatus(Integer patientId, Integer status);

    /**
     * 启用/禁用陪诊员账号
     */
    Boolean toggleEscortStatus(Integer escortId, Integer status);

    /**
     * 审核陪诊员认证（通过/驳回）
     */
    Boolean auditEscort(Integer escortId, Integer auditStatus, String auditNote);

    /**
     * 将陪诊员加入/移出黑名单
     */
    Boolean manageEscortBlacklist(Integer escortId, Integer accountStatus, String reason);

    /**
     * 分页查询所有患者
     */
    Page<UserPatientVO> listAllPatients(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 分页查询所有陪诊员
     */
    Page<EscortInfoVO> listAllEscorts(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 数据统计：按日/周/月输出核心指标
     */
    StatisticsVO getStatistics(String timeType, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取实时统计概况
     */
    DashboardVO getDashboardOverview();
}
