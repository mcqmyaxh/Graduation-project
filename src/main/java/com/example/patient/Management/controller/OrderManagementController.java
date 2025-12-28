package com.example.patient.Management.controller;

import com.example.patient.Customer.service.ServiceOrdersService;
import com.example.patient.DTO.VO.AdminOrderDetailVO;
import com.example.patient.DTO.VO.OrderMonitorVO;
import com.example.patient.util.exp.ResultData;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/Platform")
@Tag(name = "订单管理接口", description = "订单信息数据管理操作接口")
public class OrderManagementController {

    @Resource
    private ServiceOrdersService serviceOrdersService;

    /* -------------------------------------------------
     *  订单监控
     * ------------------------------------------------- */

    @GetMapping("/orders/monitor")
    @Operation(summary = "全平台订单监控（可筛选状态）")
    @PreAuthorize("@ss.hasPermission('monitorAllOrders')")
    public ResultData<Page<OrderMonitorVO>> monitorAllOrders(
            @RequestParam(defaultValue = "1")  @Parameter(description = "页码")  Integer pageNum,
            @RequestParam(defaultValue = "10") @Parameter(description = "每页条数") Integer pageSize,
            @RequestParam(required = false)    @Parameter(description = "订单状态：0待接单 1已接单 2已完成 3已取消 4投诉中") Integer status) {
        Page<OrderMonitorVO> page = serviceOrdersService.monitorAllOrders(pageNum, pageSize, status);
        return ResultData.success(page);
    }

    @GetMapping("/orders/complaint")
    @Operation(summary = "异常订单列表（状态=投诉中）")
    @PreAuthorize("@ss.hasPermission('listComplaintOrders')")
    public ResultData<Page<OrderMonitorVO>> listComplaintOrders(
            @RequestParam(defaultValue = "1")  Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<OrderMonitorVO> page = serviceOrdersService.listComplaintOrders(pageNum, pageSize);
        return ResultData.success(page);
    }

    /* -------------------------------------------------
     *  订单详情
     * ------------------------------------------------- */

    @GetMapping("/orders/{orderId}")
    @Operation(summary = "管理员查看订单详情")
    @PreAuthorize("@ss.hasPermission('getAdminOrderDetail')")
    public ResultData<AdminOrderDetailVO> getAdminOrderDetail(
            @PathVariable @Parameter(description = "订单ID") Long orderId) {
        AdminOrderDetailVO detail = serviceOrdersService.getAdminOrderDetail(orderId);
        return detail != null ? ResultData.success(detail) : ResultData.failed("订单不存在");
    }
}