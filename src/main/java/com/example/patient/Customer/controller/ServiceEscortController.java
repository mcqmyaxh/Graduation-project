package com.example.patient.Customer.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.patient.DTO.Command.RegisterEscorCommand;
import com.example.patient.DTO.Command.UpdateEscorCommand;
import com.example.patient.DTO.VO.EscorLoginVO;

import com.example.patient.Customer.entity.ServiceEscort;
import com.example.patient.Customer.service.ServiceEscortService;
import com.example.patient.util.exp.ResultData;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
@Slf4j
@RestController
@RequestMapping("/Customer")
@Tag(name = "服务端接口", description = "提供服务接口")
public class ServiceEscortController {

    @Resource
    private ServiceEscortService escortService;

    @PostMapping("/EscortRegister")
    @Operation(summary = "陪诊员注册", description = "陪诊员注册接口，需提供基本信息")
    public ResultData<Boolean> registerEscort(@Valid @RequestBody RegisterEscorCommand command) {
        log.info("陪诊员注册请求：手机号={}, 姓名={}", command.getUsername(), command.getRealName());
        Boolean result = escortService.registerEscort(command);
        return result ? ResultData.success(result, "注册成功") : ResultData.failed("注册失败，手机号已存在");
    }

    @PostMapping("/EscortLoginPhone")
    @Operation(summary = "陪诊员手机号登录", description = "陪诊员通过手机号+密码进行登录")
    public ResultData<EscorLoginVO> loginEscortByPhone(
            @Parameter(description = "手机号", required = true)
            @NotBlank(message = "手机号不能为空")
            @RequestParam String phone,

            @Parameter(description = "密码", required = true)
            @NotBlank(message = "密码不能为空")
            @RequestParam String password) {

        log.info("陪诊员手机号登录请求：phone={}", phone);
        EscorLoginVO result = escortService.loginEscortPhone(phone, password);
        return result != null ? ResultData.success(result, "登录成功") : ResultData.failed("手机号或密码错误，或账号未审核/已停用");
    }

    @PostMapping("/EscortLoginUsername")
    @Operation(summary = "陪诊员用户名登录", description = "陪诊员通过用户名+密码进行登录")
    public ResultData<EscorLoginVO> loginEscortByUsername(
            @Parameter(description = "用户名", required = true)
            @NotBlank(message = "用户名不能为空")
            @RequestParam String username,

            @Parameter(description = "密码", required = true)
            @NotBlank(message = "密码不能为空")
            @RequestParam String password) {

        log.info("陪诊员用户名登录请求：username={}", username);
        EscorLoginVO result = escortService.loginEscortname(username, password);
        return result != null ? ResultData.success(result, "登录成功") : ResultData.failed("用户名或密码错误，或账号未审核/已停用");
    }

    @PutMapping("/updateEscortInfo")
    @Operation(summary = "更新陪诊员信息", description = "更新陪诊员的个人信息")
    public ResultData<Boolean> updateEscort(@Valid @RequestBody UpdateEscorCommand command) {
        log.info("更新陪诊员信息请求：escortId={}", command.getEscortId());
        Boolean result = escortService.updateEscortInfo(command);
        return result ? ResultData.success(result, "更新成功") : ResultData.failed("更新失败，陪诊员不存在");
    }

    @GetMapping("/listEscortInfo")
    @Operation(summary = "分页获取陪诊员列表", description = "获取所有陪诊员信息列表（分页查询）")
    public ResultData<Page<ServiceEscort>> listEscorts(
            @Parameter(description = "页码", example = "1")
            @Min(value = 1, message = "页码必须大于0")
            @RequestParam(defaultValue = "1") Integer pageNumber,

            @Parameter(description = "每页大小", example = "10")
            @Min(value = 1, message = "每页大小必须大于0")
            @RequestParam(defaultValue = "10") Integer pageSize) {

        log.info("查询陪诊员列表：pageNumber={}, pageSize={}", pageNumber, pageSize);
        Page<ServiceEscort> page = new Page<>(pageNumber, pageSize);
        Page<ServiceEscort> result = escortService.listEscortInfo(page);
        return result != null ? ResultData.success(result, "获取成功") : ResultData.failed("获取失败");
    }

    @GetMapping("/getEscortInfo")
    @Operation(summary = "获取陪诊员信息", description = "根据ID获取单个陪诊员的详细信息")
    public ResultData<EscorLoginVO> getEscortInfo(
            @Parameter(description = "陪诊员ID", required = true, example = "1")
            @Min(value = 1, message = "陪诊员ID必须大于0")
            @RequestParam Integer escortId) {

        log.info("查询陪诊员信息：escortId={}", escortId);
        EscorLoginVO result = escortService.getEscortInfo(escortId);
        return result != null ? ResultData.success(result, "获取成功") : ResultData.failed("陪诊员信息不存在");
    }

    @PutMapping("/updateAuditStatus")
    @Operation(summary = "审核陪诊员", description = "更新陪诊员的审核状态")
    public ResultData<Boolean> updateAuditStatus(
            @Parameter(description = "陪诊员ID", required = true)
            @Min(value = 1, message = "陪诊员ID必须大于0")
            @RequestParam Integer escortId,

            @Parameter(description = "审核状态", required = true, example = "1")
            @Min(value = 0, message = "审核状态必须为0-2")
            @Max(value = 2, message = "审核状态必须为0-2")
            @RequestParam Integer auditStatus) {

        log.info("更新陪诊员审核状态：escortId={}, auditStatus={}", escortId, auditStatus);
        ServiceEscort escort = new ServiceEscort();
        escort.setEscortId(escortId);
        escort.setAuditStatus(auditStatus);
        Boolean result = escortService.updateById(escort);
        return result ? ResultData.success(result, "审核状态更新成功") : ResultData.failed("审核状态更新失败");
    }

    @PutMapping("/updateAccountStatus")
    @Operation(summary = "更新陪诊员账号状态", description = "更新陪诊员的账号状态（正常/暂停/封号）")
    public ResultData<Boolean> updateAccountStatus(
            @Parameter(description = "陪诊员ID", required = true)
            @Min(value = 1, message = "陪诊员ID必须大于0")
            @RequestParam Integer escortId,

            @Parameter(description = "账号状态", required = true, example = "1")
            @Min(value = 1, message = "账号状态必须为1-3")
            @Max(value = 3, message = "账号状态必须为1-3")
            @RequestParam Integer accountStatus) {

        log.info("更新陪诊员账号状态：escortId={}, accountStatus={}", escortId, accountStatus);
        ServiceEscort escort = new ServiceEscort();
        escort.setEscortId(escortId);
        escort.setAccountStatus(accountStatus);
        Boolean result = escortService.updateById(escort);
        return result ? ResultData.success(result, "账号状态更新成功") : ResultData.failed("账号状态更新失败");
    }
}
