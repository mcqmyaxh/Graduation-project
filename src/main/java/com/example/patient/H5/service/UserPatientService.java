package com.example.patient.H5.service;

import com.example.patient.Customer.entity.ServiceOrders;
import com.example.patient.DTO.Command.DeleteUserCommand;
import com.example.patient.DTO.Command.RegisterUserCommand;
import com.example.patient.DTO.Command.UpdateUserCommand;
import com.example.patient.DTO.DTO.CreateOrderDTO;
import com.example.patient.DTO.DTO.ServiceComplaintDTO;
import com.example.patient.DTO.DTO.ServiceEvaluationDTO;
import com.example.patient.DTO.DTO.UpdateOrderDTO;
import com.example.patient.DTO.VO.H5LoginVO;
import com.example.patient.DTO.VO.OrderStatusVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.example.patient.H5.entity.UserPatient;


/**
 * 患者/家属信息表 服务层。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
public interface UserPatientService extends IService<UserPatient> {
    /**
     * 患者/用户注册
     * @param command 注册命令对象
     * @return 注册是否成功
     */
    Boolean registerPatient(RegisterUserCommand command);


    /**
     * 患者/用户登录：手机号+密码
     * @param phone 手机号
     * @param password 密码
     * @return 登录用户信息VO
     */
    H5LoginVO loginPatientPhone(String phone, String password);

    /**
     * 患者/用户登录：用户名+密码
     * @param username 用户名
     * @param password 密码
     * @return 登录用户信息VO
     */
    H5LoginVO loginPatientname(String username, String password);

    /**
     * 更新患者/用户个人信息
     * @param command 更新命令对象
     * @return 更新是否成功
     */
    Boolean updateH5Patient(UpdateUserCommand command);

    /**
     * 分页获取所有患者/用户信息
     * @param page 分页对象
     * @return 分页结果
     */
    Page<UserPatient> listPatientInfo(Page<UserPatient> page);

    /**
     * 根据ID查询患者/用户信息
     * @param patientId 患者ID
     * @return 患者/用户信息VO
     */
    H5LoginVO h5GetPatientInfo(Integer patientId);

    /**
     * 逻辑删除/注销患者/用户账号
     * @param command 删除命令对象
     * @return 删除是否成功
     */
    Boolean deleteH5PatientInfo(DeleteUserCommand command);

    /**
     * 患者用户创建看病陪诊订单
     * @param orderDTO 订单创建参数
     * @return 是否创建成功
     */
    Boolean createServiceOrder(CreateOrderDTO orderDTO);

    /**
     * 患者用户查看自己的所有历史订单
     * @param patientId 患者ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 订单分页列表
     */
    Page<ServiceOrders> listPatientOrders(Integer patientId, Integer pageNum, Integer pageSize);

    /**
     * 订单结束后用户对相关订单的陪诊员打分和做出评价
     * @param evaluationDTO 评价信息
     * @return 是否评价成功
     */
    Boolean submitServiceEvaluation(ServiceEvaluationDTO evaluationDTO);

    /**
     * 用户患者对相关订单的陪诊员进行投诉
     * @param complaintDTO 投诉信息
     * @return 是否投诉成功
     */
    Boolean submitServiceComplaint(ServiceComplaintDTO complaintDTO);

    /**
     * 查看订单状态（是否被接单）
     * @param orderId 订单ID
     * @return 订单状态信息
     */
    OrderStatusVO checkOrderStatus(Long orderId);

    /**
     * 根据状态查询订单
     */
    Page<ServiceOrders> getOrdersByStatus(Integer patientId, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 更新订单信息
     */
    Boolean updateOrderInfo(Long orderId, Integer patientId, UpdateOrderDTO updateOrderDTO);

    /**
     * 根据订单ID和患者ID获取订单（验证权限）
     */
    ServiceOrders getOrderByIdAndPatientId(Long orderId, Integer patientId);

    /**
     * 患者取消订单
     * @param orderId 订单ID
     * @param patientId 患者ID（用于权限验证）
     * @return 是否取消成功
     */
    Boolean cancelOrder(Long orderId, Integer patientId);
}
