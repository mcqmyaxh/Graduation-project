package com.example.patient.Management.controller;

import com.example.patient.DTO.VO.DashboardVO;
import com.example.patient.DTO.VO.EscortInfoVO;
import com.example.patient.DTO.VO.StatisticsVO;
import com.example.patient.DTO.VO.UserPatientVO;
import com.example.patient.Management.service.ManagementAdminService;
import com.example.patient.util.exp.ResultData;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/Platform")
@Tag(name = "后台数据信息接口", description = "管理员数据信息管理操作接口")
public class PlatformDataController {
    @Resource
    private ManagementAdminService managementAdminService;

    /* -------------------------------------------------
     *  患者、陪诊员管理
     * ------------------------------------------------- */

    @PutMapping("/patient/status")
    @Operation(summary = "启用/禁用患者账号")
    @PreAuthorize("@ss.hasPermission('togglePatientStatus')")
    public ResultData<Boolean> togglePatientStatus(@RequestParam Integer patientId,
                                                   @RequestParam Integer status) {
        boolean ok = managementAdminService.togglePatientStatus(patientId, status);
        return ok ? ResultData.success(true) : ResultData.failed("操作失败");
    }

    @PutMapping("/escort/status")
    @Operation(summary = "启用/禁用陪诊员账号")
    @PreAuthorize("@ss.hasPermission('toggleEscortStatus')")
    public ResultData<Boolean> toggleEscortStatus(@RequestParam Integer escortId,
                                                  @RequestParam Integer status) {
        boolean ok = managementAdminService.toggleEscortStatus(escortId, status);
        return ok ? ResultData.success(true) : ResultData.failed("操作失败");
    }

    @PutMapping("/escort/audit")
    @Operation(summary = "审核陪诊员认证")
    @PreAuthorize("@ss.hasPermission('auditEscort')")
    public ResultData<Boolean> auditEscort(@RequestParam Integer escortId,
                                           @RequestParam Integer auditStatus,
                                           @RequestParam(required = false) String auditNote) {
        boolean ok = managementAdminService.auditEscort(escortId, auditStatus, auditNote);
        return ok ? ResultData.success(true) : ResultData.failed("审核失败");
    }

    @PutMapping("/escort/blacklist")
    @Operation(summary = "陪诊员加入/移出黑名单")
    @PreAuthorize("@ss.hasPermission('manageEscortBlacklist')")
    public ResultData<Boolean> manageEscortBlacklist(@RequestParam Integer escortId,
                                                     @RequestParam Integer accountStatus,
                                                     @RequestParam(required = false) String reason) {
        boolean ok = managementAdminService.manageEscortBlacklist(escortId, accountStatus, reason);
        return ok ? ResultData.success(true) : ResultData.failed("操作失败");
    }

    /* -------------------------------------------------
     *  列表查询
     * ------------------------------------------------- */

    @GetMapping("/patients")
    @Operation(summary = "分页查询所有患者")
    @PreAuthorize("@ss.hasPermission('listAllPatients')")
    public ResultData<Page<UserPatientVO>> listAllPatients(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        Page<UserPatientVO> page = managementAdminService.listAllPatients(pageNum, pageSize, keyword);
        return ResultData.success(page);
    }

    @GetMapping("/escorts")
    @Operation(summary = "分页查询所有陪诊员")
    @PreAuthorize("@ss.hasPermission('listAllEscorts')")
    public ResultData<Page<EscortInfoVO>> listAllEscorts(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        Page<EscortInfoVO> page = managementAdminService.listAllEscorts(pageNum, pageSize, keyword);
        return ResultData.success(page);
    }

    /* -------------------------------------------------
     *  数据看板
     * ------------------------------------------------- */

    @GetMapping("/statistics")
    @Operation(summary = "核心指标统计（日/周/月）")
    @PreAuthorize("@ss.hasPermission('getStatistics')")
    public ResultData<StatisticsVO> getStatistics(
            @Parameter(description = "day|week|month") @RequestParam String timeType,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime endTime) {
        StatisticsVO vo = managementAdminService.getStatistics(timeType, startTime, endTime);
        return vo != null ? ResultData.success(vo) : ResultData.failed("统计失败");
    }

    @GetMapping("/dashboard")
    @Operation(summary = "实时概况-今日核心指标")
    @PreAuthorize("@ss.hasPermission('getDashboardOverview')")
    public ResultData<DashboardVO> getDashboardOverview() {
        DashboardVO vo = managementAdminService.getDashboardOverview();
        return vo != null ? ResultData.success(vo) : ResultData.failed("查询失败");
    }

}
