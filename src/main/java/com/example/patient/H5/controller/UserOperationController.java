package com.example.patient.H5.controller;


import com.example.patient.DTO.DTO.CreateOrderDTO;
import com.example.patient.DTO.DTO.ServiceComplaintDTO;
import com.example.patient.DTO.DTO.ServiceEvaluationDTO;
import com.example.patient.DTO.DTO.UpdateOrderDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.patient.DTO.VO.OrderStatusVO;
import com.example.patient.Customer.entity.ServiceOrders;
import com.example.patient.H5.service.UserPatientService;
import com.example.patient.util.exp.ResultData;
import com.example.patient.util.exp.ResultCode;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/H5")
@Tag(name = "病患/用户程序操作接口", description = "提供用户程序操作平台的操作接口")
public class UserOperationController {
    @Resource
    private UserPatientService userPatientService;

    @Operation(summary = "创建看病陪诊订单", description = "患者用户创建看病陪诊订单")
    @PostMapping("/order")
    public ResultData<Boolean> createServiceOrder(
            @Valid @RequestBody CreateOrderDTO orderDTO) {
        log.info("患者创建订单请求：患者ID={}, 医院ID={}, 科室ID={}",
                orderDTO.getPatientId(), orderDTO.getHospitalId(), orderDTO.getDeptId());

        Boolean result = userPatientService.createServiceOrder(orderDTO);
        return result ? ResultData.success(result, "订单创建成功") : ResultData.failed("订单创建失败");
    }

    @Operation(summary = "查看历史订单", description = "患者用户查看自己的所有历史订单")
    @GetMapping("/orders")
    public ResultData<Page<ServiceOrders>> listPatientOrders(
            @Parameter(description = "患者ID", required = true)
            @RequestParam Integer patientId,

            @Parameter(description = "页码，从1开始", example = "1")
            @RequestParam(defaultValue = "1") Integer pageNum,

            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize) {

        log.info("患者查询订单列表请求：患者ID={}, 页码={}, 页大小={}", patientId, pageNum, pageSize);

        Page<ServiceOrders> orders = userPatientService.listPatientOrders(patientId, pageNum, pageSize);
        if (orders != null && orders.getRecords() != null) {
            return ResultData.success(orders, "查询成功");
        }
        return ResultData.failed("查询失败或暂无订单");
    }

    @Operation(summary = "提交服务评价", description = "订单结束后用户对相关订单的陪诊员打分和做出评价")
    @PostMapping("/evaluation")
    public ResultData<Boolean> submitServiceEvaluation(
            @Valid @RequestBody ServiceEvaluationDTO evaluationDTO) {

        log.info("提交服务评价请求：订单ID={}, 患者ID={}, 评分={}",
                evaluationDTO.getOrderId(), evaluationDTO.getPatientId(), evaluationDTO.getScore());

        Boolean result = userPatientService.submitServiceEvaluation(evaluationDTO);
        return result ? ResultData.success(result, "评价提交成功") : ResultData.failed("评价提交失败");
    }

    @Operation(summary = "提交投诉", description = "用户患者对相关订单的陪诊员进行投诉")
    @PostMapping("/complaint")
    public ResultData<Boolean> submitServiceComplaint(
            @Valid @RequestBody ServiceComplaintDTO complaintDTO) {

        log.info("提交投诉请求：订单ID={}, 投诉人ID={}, 被投诉人ID={}",
                complaintDTO.getOrderId(), complaintDTO.getComplainantId(), complaintDTO.getRespondentId());

        Boolean result = userPatientService.submitServiceComplaint(complaintDTO);
        return result ? ResultData.success(result, "投诉提交成功") : ResultData.failed("投诉提交失败");
    }

    @Operation(summary = "查看订单状态", description = "查看订单状态（是否被接单）")
    @GetMapping("/order/status/{orderId}")
    public ResultData<OrderStatusVO> checkOrderStatus(
            @Parameter(description = "订单ID", required = true)
            @PathVariable Long orderId) {

        log.info("查询订单状态请求：订单ID={}", orderId);

        OrderStatusVO statusVO = userPatientService.checkOrderStatus(orderId);
        if (statusVO == null) {
            return ResultData.failed(ResultCode.NOT_FOUND.getCode(), "订单不存在");
        }
        return ResultData.success(statusVO, "查询成功");
    }

    @Operation(summary = "获取订单详情", description = "根据订单ID获取订单详细信息")
    @GetMapping("/order/{orderId}")
    public ResultData<ServiceOrders> getOrderDetail(
            @Parameter(description = "订单ID", required = true)
            @PathVariable Long orderId,

            @Parameter(description = "患者ID（用于权限验证）", required = true)
            @RequestParam Integer patientId) {

        log.info("获取订单详情请求：订单ID={}, 患者ID={}", orderId, patientId);

        ServiceOrders order = userPatientService.getOrderByIdAndPatientId(orderId, patientId);
        if (order == null) {
            return ResultData.failed(ResultCode.FORBIDDEN.getCode(), "订单不存在或无权限访问");
        }
        return ResultData.success(order, "查询成功");
    }

    @Operation(summary = "取消订单", description = "患者取消未接单的订单")
    @PutMapping("/order/{orderId}/cancel")
    public ResultData<Boolean> cancelOrder(
            @Parameter(description = "订单ID", required = true)
            @PathVariable Long orderId,

            @Parameter(description = "患者ID（用于权限验证）", required = true)
            @RequestParam Integer patientId) {

        log.info("取消订单请求：订单ID={}, 患者ID={}", orderId, patientId);

        Boolean result = userPatientService.cancelOrder(orderId, patientId);
        return result ? ResultData.success(result, "订单取消成功") : ResultData.failed("订单取消失败");
    }

    @Operation(summary = "获取指定状态的订单", description = "获取患者特定状态的订单列表")
    @GetMapping("/orders/status")
    public ResultData<Page<ServiceOrders>> getOrdersByStatus(
            @Parameter(description = "患者ID", required = true)
            @RequestParam Integer patientId,

            @Parameter(description = "订单状态：0待接单,1已接单,2已完成,3已取消,4投诉中", required = true)
            @RequestParam Integer status,

            @Parameter(description = "页码，从1开始", example = "1")
            @RequestParam(defaultValue = "1") Integer pageNum,

            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize) {

        log.info("查询特定状态订单请求：患者ID={}, 状态={}, 页码={}, 页大小={}",
                patientId, status, pageNum, pageSize);

        Page<ServiceOrders> orders = userPatientService.getOrdersByStatus(patientId, status, pageNum, pageSize);
        if (orders != null && orders.getRecords() != null) {
            return ResultData.success(orders, "查询成功");
        }
        return ResultData.failed("查询失败");
    }

    @Operation(summary = "更新订单信息", description = "更新订单的部分信息（如病情描述、预约时间等）")
    @PutMapping("/order/{orderId}")
    public ResultData<Boolean> updateOrderInfo(
            @Parameter(description = "订单ID", required = true)
            @PathVariable Long orderId,

            @Parameter(description = "患者ID（用于权限验证）", required = true)
            @RequestParam Integer patientId,

            @Valid @RequestBody UpdateOrderDTO updateOrderDTO) {

        log.info("更新订单信息请求：订单ID={}, 患者ID={}", orderId, patientId);

        Boolean result = userPatientService.updateOrderInfo(orderId, patientId, updateOrderDTO);
        return result ? ResultData.success(result, "订单更新成功") : ResultData.failed("订单更新失败");
    }
}