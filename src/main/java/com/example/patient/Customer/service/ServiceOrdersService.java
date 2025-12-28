package com.example.patient.Customer.service;

import com.example.patient.DTO.VO.AdminOrderDetailVO;
import com.example.patient.DTO.VO.OrderMonitorVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.example.patient.Customer.entity.ServiceOrders;

/**
 * 陪诊订单表 服务层。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
public interface ServiceOrdersService extends IService<ServiceOrders> {
    /**
     * 查看全平台订单（可筛选状态）
     */
    Page<OrderMonitorVO> monitorAllOrders(Integer pageNum, Integer pageSize, Integer status);

    /**
     * 查看异常订单（投诉中的订单）
     */
    Page<OrderMonitorVO> listComplaintOrders(Integer pageNum, Integer pageSize);

    /**
     * 查看订单详情（管理员版）
     */
    AdminOrderDetailVO getAdminOrderDetail(Long orderId);
}
