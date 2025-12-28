package com.example.patient.Customer.controller;

import com.example.patient.Customer.entity.ServiceOrders;
import com.example.patient.Customer.service.ServiceEscortService;

import com.example.patient.DTO.DTO.OrderAcceptDTO;
import com.example.patient.DTO.DTO.OrderCompleteDTO;
import com.example.patient.DTO.VO.EscortOrderDetailVO;
import com.example.patient.registry.infrastructure.common.api.ResultCode;
import com.example.patient.util.exp.ResultData;

import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/escort/order")
@Tag(name = "陪诊员订单管理接口", description = "提供陪诊员订单相关操作接口")
public class EscortOrderController {

    @Resource
    private ServiceEscortService serviceEscortService;

    @Operation(summary = "查看已接订单", description = "陪诊员查看自身接过的所有订单")
    @GetMapping("/list")
    public ResultData<Page<ServiceOrders>> listEscortOrders(
            @Parameter(description = "陪诊员ID", required = true)
            @RequestParam Integer escortId,

            @Parameter(description = "页码，从1开始", example = "1")
            @RequestParam(defaultValue = "1") Integer pageNum,

            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize) {

        log.info("陪诊员查看订单列表：陪诊员ID={}, 页码={}, 页大小={}", escortId, pageNum, pageSize);

        Page<ServiceOrders> orders = serviceEscortService.listEscortOrders(escortId, pageNum, pageSize);
        if (orders != null && orders.getRecords() != null) {
            return ResultData.success(orders, "查询成功");
        }
        return ResultData.failed("查询失败");
    }

    @Operation(summary = "查看待接订单", description = "陪诊员查看订单大厅（待接单的订单）")
    @GetMapping("/available")
    public ResultData<Page<ServiceOrders>> listAvailableOrders(
            @Parameter(description = "页码，从1开始", example = "1")
            @RequestParam(defaultValue = "1") Integer pageNum,

            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize,

            @Parameter(description = "陪诊员ID（用于检查时间冲突）", required = true)
            @RequestParam Integer escortId) {

        log.info("陪诊员查看待接订单：页码={}, 页大小={}, 陪诊员ID={}", pageNum, pageSize, escortId);

        Page<ServiceOrders> orders = serviceEscortService.listAvailableOrders(pageNum, pageSize, escortId);
        if (orders != null && orders.getRecords() != null) {
            return ResultData.success(orders, "查询成功");
        }
        return ResultData.failed("查询失败");
    }

    @Operation(summary = "接单", description = "陪诊员接单")
    @PostMapping("/accept")
    public ResultData<Boolean> acceptOrder(
            @Valid @RequestBody OrderAcceptDTO acceptDTO) {

        log.info("陪诊员接单：订单ID={}, 陪诊员ID={}", acceptDTO.getOrderId(), acceptDTO.getEscortId());

        Boolean result = serviceEscortService.acceptOrder(acceptDTO.getOrderId(), acceptDTO.getEscortId());
        return result ? ResultData.success(result, "接单成功") : ResultData.failed("接单失败");
    }

    @Operation(summary = "取消接单", description = "陪诊员取消接单")
    @PutMapping("/cancel-accept")
    public ResultData<Boolean> cancelAcceptOrder(
            @Valid @RequestBody OrderAcceptDTO acceptDTO) {

        log.info("陪诊员取消接单：订单ID={}, 陪诊员ID={}", acceptDTO.getOrderId(), acceptDTO.getEscortId());

        Boolean result = serviceEscortService.cancelAcceptOrder(acceptDTO.getOrderId(), acceptDTO.getEscortId());
        return result ? ResultData.success(result, "取消接单成功") : ResultData.failed("取消接单失败");
    }

    @Operation(summary = "结束订单", description = "陪诊员结束订单")
    @PutMapping("/complete")
    public ResultData<Boolean> completeOrder(
            @Valid @RequestBody OrderCompleteDTO completeDTO) {

        log.info("陪诊员结束订单：订单ID={}, 陪诊员ID={}", completeDTO.getOrderId(), completeDTO.getEscortId());

        Boolean result = serviceEscortService.completeOrder(completeDTO.getOrderId(), completeDTO.getEscortId());
        return result ? ResultData.success(result, "订单结束成功") : ResultData.failed("订单结束失败");
    }

    @Operation(summary = "查看被投诉订单", description = "查看被投诉的订单")
    @GetMapping("/complainted")
    public ResultData<Page<ServiceOrders>> listComplaintedOrders(
            @Parameter(description = "陪诊员ID", required = true)
            @RequestParam Integer escortId,

            @Parameter(description = "页码，从1开始", example = "1")
            @RequestParam(defaultValue = "1") Integer pageNum,

            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") Integer pageSize) {

        log.info("查看被投诉订单：陪诊员ID={}, 页码={}, 页大小={}", escortId, pageNum, pageSize);

        Page<ServiceOrders> orders = serviceEscortService.listComplaintedOrders(escortId, pageNum, pageSize);
        if (orders != null && orders.getRecords() != null) {
            return ResultData.success(orders, "查询成功");
        }
        return ResultData.failed("查询失败");
    }

    @Operation(summary = "查看当前订单", description = "查看陪诊员当前正在进行中的订单")
    @GetMapping("/current")
    public ResultData<ServiceOrders> getCurrentOrder(
            @Parameter(description = "陪诊员ID", required = true)
            @RequestParam Integer escortId) {

        log.info("查看当前订单：陪诊员ID={}", escortId);

        ServiceOrders order = serviceEscortService.getCurrentOrder(escortId);
        if (order != null) {
            return ResultData.success(order, "查询成功");
        }
        return ResultData.success(null, "暂无进行中的订单");
    }

    @GetMapping("/detail/{orderId}")
    @Operation(summary = "查看订单详情", description = "陪诊员查看订单详情：包括患者信息、病情描述、疾病史等")
    public ResultData<EscortOrderDetailVO> getOrderDetail(
            @Parameter(description = "订单ID", required = true)
            @PathVariable Long orderId,

            @Parameter(description = "陪诊员ID（用于权限验证）", required = true)
            @RequestParam Integer escortId) {

        log.info("陪诊员查看订单详情：订单ID={}, 陪诊员ID={}", orderId, escortId);

        EscortOrderDetailVO detailVO = serviceEscortService.getOrderDetail(orderId, escortId);
        if (detailVO == null) {
            return ResultData.failed(ResultCode.FORBIDDEN.getCode(), "订单不存在或无权限查看");
        }

        return ResultData.success(detailVO, "查询成功");
    }
}
