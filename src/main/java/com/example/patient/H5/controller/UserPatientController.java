package com.example.patient.H5.controller;


import com.example.patient.DTO.Command.DeleteUserCommand;
import com.example.patient.DTO.Command.RegisterUserCommand;
import com.example.patient.DTO.Command.UpdateUserCommand;
import com.example.patient.DTO.VO.H5LoginVO;
import com.example.patient.H5.entity.UserPatient;
import com.example.patient.H5.service.UserPatientService;
import com.example.patient.util.exp.ResultData;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import jakarta.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/H5")
@Tag(name = "微信H5平台接口", description = "提供微信H5平台的操作接口")
public class UserPatientController {

    @Resource
    private UserPatientService userService;


    @PostMapping("/register")
    @Operation(summary = "患者/用户注册", description = "患者或家属用户注册接口，需提供基本信息")
    public ResultData<Boolean> registerPatient(@Valid @RequestBody RegisterUserCommand command) {
        log.info("患者注册请求：手机号={}", command.getPhone());
        Boolean result = userService.registerPatient(command);
        return result ? ResultData.success(result, "注册成功") : ResultData.failed("注册失败");
    }

    @PostMapping("/login/phone")
    @Operation(summary = "手机号登录", description = "患者/用户通过手机号+密码进行登录")
    public ResultData<H5LoginVO> loginByPhone(
            @Parameter(description = "手机号", required = true)
            @NotBlank(message = "手机号不能为空")
            @RequestParam String phone,

            @Parameter(description = "密码", required = true)
            @NotBlank(message = "密码不能为空")
            @RequestParam String password) {

        log.info("手机号登录请求：phone={}", phone);
        H5LoginVO result = userService.loginPatientPhone(phone, password);
        return result != null ? ResultData.success(result, "登录成功") : ResultData.failed("用户名或密码错误");
    }

    @PostMapping("/login/username")
    @Operation(summary = "用户名登录", description = "患者/用户通过用户名+密码进行登录")
    public ResultData<H5LoginVO> loginByUsername(
            @Parameter(description = "用户名", required = true)
            @NotBlank(message = "用户名不能为空")
            @RequestParam String username,

            @Parameter(description = "密码", required = true)
            @NotBlank(message = "密码不能为空")
            @RequestParam String password) {

        log.info("用户名登录请求：username={}", username);
        H5LoginVO result = userService.loginPatientname(username, password);
        return result != null ? ResultData.success(result, "登录成功") : ResultData.failed("用户名或密码错误");
    }

    @PutMapping("/update")
    @Operation(summary = "更新患者/用户信息", description = "更新患者或家属的个人信息")
    public ResultData<Boolean> updatePatient(@Valid @RequestBody UpdateUserCommand command) {
        log.info("更新患者信息请求：patientId={}", command.getPatientId());
        Boolean result = userService.updateH5Patient(command);
        return result ? ResultData.success(result, "更新成功") : ResultData.failed("更新失败");
    }

    @GetMapping("/list")
    @Operation(summary = "分页获取患者/用户列表", description = "获取所有患者/用户信息列表（分页查询）")
    public ResultData<Page<UserPatient>> listPatients(
            @Parameter(description = "页码", example = "1")
            @Min(value = 1, message = "页码必须大于0")
            @RequestParam(defaultValue = "1") Integer pageNumber,

            @Parameter(description = "每页大小", example = "10")
            @Min(value = 1, message = "每页大小必须大于0")
            @RequestParam(defaultValue = "10") Integer pageSize) {

        log.info("查询患者列表：pageNumber={}, pageSize={}", pageNumber, pageSize);
        Page<UserPatient> page = new Page<>(pageNumber, pageSize);
        Page<UserPatient> result = userService.listPatientInfo(page);
        return result != null ? ResultData.success(result, "获取成功") : ResultData.failed("获取失败");
    }

    @GetMapping("/info")
    @Operation(summary = "获取患者/用户信息", description = "根据ID获取单个患者/用户的详细信息")
    public ResultData<H5LoginVO> getPatientInfo(
            @Parameter(description = "患者ID", required = true, example = "1")
            @Min(value = 1, message = "患者ID必须大于0")
            @RequestParam Integer patientId) {

        log.info("查询患者信息：patientId={}", patientId);
        H5LoginVO result = userService.h5GetPatientInfo(patientId);
        return result != null ? ResultData.success(result, "获取成功") : ResultData.failed("患者信息不存在");
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除/注销患者账号", description = "逻辑删除/注销患者/用户账号（状态从1变为0）")
    public ResultData<Boolean> deletePatient(@Valid @RequestBody DeleteUserCommand command) {
        log.info("删除患者账号请求：patientId={}, operatorId={}", command.getPatientId(), command.getOperatorId());
        Boolean result = userService.deleteH5PatientInfo(command);
        return result ? ResultData.success(result, "删除成功") : ResultData.failed("删除失败");
    }
}
