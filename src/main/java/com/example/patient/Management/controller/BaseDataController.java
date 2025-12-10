package com.example.patient.Management.controller;

import com.example.patient.Management.entity.BaseDepartment;
import com.example.patient.Management.entity.BaseHospital;
import com.example.patient.Management.entity.BaseHospitalDeptMapping;
import com.example.patient.Management.service.BaseDepartmentService;
import com.example.patient.Management.service.BaseHospitalDeptMappingService;
import com.example.patient.Management.service.BaseHospitalService;
import com.example.patient.util.exp.ResultData;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/base")
@Tag(name = "基础数据管理", description = "医院、科室及号源维护")
@Slf4j
@RequiredArgsConstructor // 关键：自动生成构造函数注入Service，解决 resolve symbol 问题
public class BaseDataController {

    @Resource
    private BaseHospitalService hospitalService;

    @Resource
    private BaseDepartmentService departmentService;

    @Resource
    private BaseHospitalDeptMappingService mappingService;

    // ================== 医院管理 ==================

    @GetMapping("/pageHospital")
    @Operation(summary = "分页查询医院", description = "支持按医院名称模糊查询")
    public ResultData<Page<BaseHospital>> pageHospital(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name) {
        return ResultData.success(hospitalService.pageHospital(pageNumber, pageSize, name));
    }

    @PostMapping("/hospital/save")
    @Operation(summary = "新增/编辑医院", description = "有ID为编辑，无ID为新增")
    public ResultData<String> saveHospital(@RequestBody BaseHospital hospital) {
        boolean result = hospitalService.saveOrUpdate(hospital);
        return result ? ResultData.success("保存成功") : ResultData.failed("保存失败");
    }

    @PostMapping("/hospital/status")
    @Operation(summary = "停用/启用医院")
    public ResultData<String> updateHospitalStatus(@RequestParam Integer hospitalId, @RequestParam Integer status) {
        boolean result = hospitalService.updateHospitalStatus(hospitalId, status);
        return result ? ResultData.success("操作成功") : ResultData.failed("操作失败");
    }

    // ================== 科室管理 ==================

    @GetMapping("/listByHospitalDepartmentId")
    @Operation(summary = "查询医院下属科室", description = "根据医院ID获取科室列表")
    public ResultData<List<BaseDepartment>> listDept(@RequestParam Integer hospitalId) {
        return ResultData.success(departmentService.listByHospitalDepartmentId(hospitalId));
    }

    @PostMapping("/dept/save")
    @Operation(summary = "新增/编辑科室")
    public ResultData<String> saveDept(@RequestBody BaseDepartment dept) {
        boolean result = departmentService.saveOrUpdate(dept);
        return result ? ResultData.success("保存成功") : ResultData.failed("保存失败");
    }

    @PostMapping("/updateHospitalDepartmentStatus")
    @Operation(summary = "停用/启用科室")
    public ResultData<String> updateDeptStatus(@RequestParam Integer deptId, @RequestParam Integer status) {
        boolean result = departmentService.updateHospitalDepartmentStatus(deptId, status);
        return result ? ResultData.success("操作成功") : ResultData.failed("操作失败");
    }

    // ================== 号源映射配置 ==================

    @PostMapping("/configDepartmentSlots")
    @Operation(summary = "设置科室号源", description = "配置某医院某科室的号源上限")
    public ResultData<String> configMapping(@RequestBody BaseHospitalDeptMapping mapping) {
        try {
            boolean result = mappingService.configDepartmentSlots(mapping);
            return result ? ResultData.success("配置成功") : ResultData.failed("配置失败");
        } catch (Exception e) {
            log.error("号源配置失败", e);
            return ResultData.failed("配置异常: " + e.getMessage());
        }
    }

    // ================== 医院详情 ==================
    @GetMapping("/hospital/detail")
    @Operation(summary = "获取医院详情")
    public ResultData<BaseHospital> getHospitalDetail(@RequestParam Integer hospitalId) {
        BaseHospital hospital = hospitalService.getById(hospitalId); // 或调用新定义的 getHospitalById
        return ResultData.success(hospital);
    }

    // ================== 科室详情 ==================
    @GetMapping("/dept/detail")
    @Operation(summary = "获取科室详情")
    public ResultData<BaseDepartment> getDeptDetail(@RequestParam Integer deptId) {
        BaseDepartment dept = departmentService.getById(deptId);
        return ResultData.success(dept);
    }

    // ================== 号源配置查询 ==================
    @GetMapping("/mapping/list")
    @Operation(summary = "查询某医院的号源配置列表")
    public ResultData<List<BaseHospitalDeptMapping>> listMappings(@RequestParam Integer hospitalId) {
        return ResultData.success(mappingService.listByHospitalId(hospitalId));
    }
}