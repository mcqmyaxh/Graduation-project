package com.example.patient.Customer.service;

import com.example.patient.Customer.entity.ServiceOrders;
import com.example.patient.DTO.Command.*;
import com.example.patient.DTO.VO.EscorLoginVO;

import com.example.patient.DTO.VO.EscortOrderDetailVO;
import com.example.patient.DTO.VO.H5LoginVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.example.patient.Customer.entity.ServiceEscort;

/**
 * 陪诊员信息表 服务层。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
public interface ServiceEscortService extends IService<ServiceEscort> {
    /**
     * 陪诊员注册
     * @param command 注册命令
     * @return 注册成功返回true
     */
    Boolean registerEscort(RegisterEscorCommand command);

    /**
     * 陪诊员登录：手机号+密码
     * @param phone 手机号
     * @param password 密码
     * @return 登录管理员信息VO
     */
    EscorLoginVO loginEscortPhone(String phone, String password);

    /**
     * 陪诊员登录：账号+密码
     * @param username 用户名
     * @param password 密码
     * @return 登录管理员信息VO
     */
    EscorLoginVO loginEscortname(String username, String password);

    /**
     * 陪诊员更新个人信息
     * @param command 更新命令
     * @return 更新成功返回true
     */
    Boolean updateEscortInfo(UpdateEscorCommand command);

    /**
     * 无条件分页获取所有陪诊员信息
     * @param page 分页参数
     * @return 陪诊员分页列表
     */
    Page<ServiceEscort> listEscortInfo(Page<ServiceEscort> page);

    /**
     * 根据escortId查询陪诊员信息
     * @param escortId 陪诊员ID
     * @return 陪诊员信息VO
     */
    EscorLoginVO getEscortInfo(Integer escortId);

    /**
     * 陪诊员查看自身接过的所有订单
     * @param escortId 陪诊员ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 订单分页列表
     */
    Page<ServiceOrders> listEscortOrders(Integer escortId, Integer pageNum, Integer pageSize);

    /**
     * 陪诊员查看订单大厅（待接单的订单）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param escortId 陪诊员ID（用于检查时间冲突）
     * @return 待接单订单分页列表
     */
    Page<ServiceOrders> listAvailableOrders(Integer pageNum, Integer pageSize, Integer escortId);

    /**
     * 陪诊员接单
     * @param orderId 订单ID
     * @param escortId 陪诊员ID
     * @return 接单是否成功
     */
    Boolean acceptOrder(Long orderId, Integer escortId);

    /**
     * 陪诊员取消接单
     * @param orderId 订单ID
     * @param escortId 陪诊员ID
     * @return 取消是否成功
     */
    Boolean cancelAcceptOrder(Long orderId, Integer escortId);

    /**
     * 陪诊员结束订单
     * @param orderId 订单ID
     * @param escortId 陪诊员ID
     * @return 结束是否成功
     */
    Boolean completeOrder(Long orderId, Integer escortId);

    /**
     * 查看被投诉的订单
     * @param escortId 陪诊员ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 被投诉订单分页列表
     */
    Page<ServiceOrders> listComplaintedOrders(Integer escortId, Integer pageNum, Integer pageSize);

    /**
     * 查看陪诊员当前正在进行中的订单
     * @param escortId 陪诊员ID
     * @return 当前订单信息
     */
    ServiceOrders getCurrentOrder(Integer escortId);

    /**
     * 订单详情：查看患者信息、病情描述、疾病史
     * @param orderId 订单ID
     * @param escortId 陪诊员ID（用于权限验证）
     * @return 订单详情信息
     */
    EscortOrderDetailVO getOrderDetail(Long orderId, Integer escortId);
}
