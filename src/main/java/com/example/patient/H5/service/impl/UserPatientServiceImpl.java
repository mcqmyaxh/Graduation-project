package com.example.patient.H5.service.impl;

import com.example.patient.Customer.entity.ServiceComplaint;
import com.example.patient.Customer.entity.ServiceEvaluation;
import com.example.patient.Customer.entity.ServiceOrders;
import com.example.patient.Customer.mapper.ServiceComplaintMapper;
import com.example.patient.Customer.mapper.ServiceEvaluationMapper;
import com.example.patient.Customer.mapper.ServiceOrdersMapper;
import com.example.patient.DTO.Command.DeleteUserCommand;
import com.example.patient.DTO.Command.RegisterUserCommand;
import com.example.patient.DTO.Command.UpdateUserCommand;
import com.example.patient.DTO.DTO.CreateOrderDTO;
import com.example.patient.DTO.DTO.ServiceComplaintDTO;
import com.example.patient.DTO.DTO.ServiceEvaluationDTO;
import com.example.patient.DTO.DTO.UpdateOrderDTO;
import com.example.patient.DTO.VO.H5LoginVO;
import com.example.patient.DTO.VO.OrderStatusVO;
import com.example.patient.registry.infrastructure.common.utils.StringUtils;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.H5.entity.UserPatient;
import com.example.patient.H5.mapper.UserPatientMapper;
import com.example.patient.H5.service.UserPatientService;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * 患者/家属信息表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Slf4j
@Service
public class UserPatientServiceImpl extends ServiceImpl<UserPatientMapper, UserPatient>
        implements UserPatientService{

    @Resource
    private UserPatientMapper userPatientMapper;

    @Resource
    private ServiceOrdersMapper serviceOrdersMapper;

    @Resource
    private ServiceEvaluationMapper serviceEvaluationMapper;

    @Resource
    private ServiceComplaintMapper serviceComplaintMapper;

    /**
     * 患者/用户注册
     */
    @Override
    public Boolean registerPatient(RegisterUserCommand command) {
        try {
            // 1. 检查手机号是否已存在
            QueryWrapper checkWrapper = QueryWrapper.create()
                    .eq(UserPatient::getPhone, command.getPhone());
            UserPatient existingUser = this.getOne(checkWrapper);
            if (existingUser != null) {
                log.warn("注册失败：手机号 {} 已存在", command.getPhone());
                return false;
            }

            // 2. 构建实体对象
            UserPatient userPatient = UserPatient.builder()
                    .username(command.getPhone()) // 默认用手机号作为用户名
                    .password(command.getPassword())
                    .realName(command.getRealName())
                    .gender(command.getGender())
                    .age(command.getAge())
                    .medicalHistory(command.getMedicalHistory())
                    .phone(command.getPhone())
                    .status(1) // 默认为启用状态
                    .gmtCreate(LocalDateTime.now())
                    .gmtModified(LocalDateTime.now())
                    .build();

            // 3. 保存到数据库
            boolean saved = this.save(userPatient);
            if (saved) {
                log.info("患者注册成功：手机号 {}", command.getPhone());
            }
            return saved;

        } catch (Exception e) {
            log.error("患者注册失败，手机号：{}", command.getPhone(), e);
            return false;
        }
    }

    /**
     * 患者/用户登录：手机号+密码
     *
     * @param phone    手机号
     * @param password 密码
     * @return 登录用户信息VO
     */
    @Override
    public H5LoginVO loginPatientPhone(String phone, String password) {
        try {
            if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
                log.warn("手机号或密码不能为空");
                return null;
            }

            // 构建查询条件：手机号匹配 + 密码匹配 + 状态为启用
            QueryWrapper wrapper = QueryWrapper.create()
                    .eq("phone", phone)
                    .eq("password", password)
                    .eq("status", 1); // 只允许状态为启用的用户登录

            UserPatient userPatient = userPatientMapper.selectOneByQuery(wrapper);
            if (userPatient == null) {
                log.warn("手机号登录失败：手机号 {} 不存在或密码错误", phone);
                return null;
            }

            // 构建返回的VO对象
            H5LoginVO h5LoginVO = H5LoginVO.builder()
                    .patientId(userPatient.getPatientId())
                    .username(userPatient.getUsername())
                    .realName(userPatient.getRealName())
                    .gender(userPatient.getGender())
                    .age(userPatient.getAge())
                    .medicalHistory(userPatient.getMedicalHistory())
                    .phone(userPatient.getPhone())
                    .status(userPatient.getStatus())
                    .gmtCreate(userPatient.getGmtCreate())
                    .build();

            log.info("手机号登录成功：ID {}, 手机号 {}", userPatient.getPatientId(), phone);
            return h5LoginVO;

        } catch (Exception e) {
            log.error("手机号登录失败，手机号：{}", phone, e);
            return null;
        }
    }

    /**
     * 患者/用户登录：用户名+密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录用户信息VO
     */
    @Override
    public H5LoginVO loginPatientname(String username, String password) {
        try {
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                log.warn("用户名或密码不能为空");
                return null;
            }

            // 构建查询条件：用户名匹配 + 密码匹配 + 状态为启用
            QueryWrapper wrapper = QueryWrapper.create()
                    .eq("username", username)
                    .eq("password", password)
                    .eq("status", 1); // 只允许状态为启用的用户登录

            UserPatient userPatient = userPatientMapper.selectOneByQuery(wrapper);
            if (userPatient == null) {
                log.warn("用户名登录失败：用户名 {} 不存在或密码错误", username);
                return null;
            }

            // 构建返回的VO对象
            H5LoginVO h5LoginVO = H5LoginVO.builder()
                    .patientId(userPatient.getPatientId())
                    .username(userPatient.getUsername())
                    .realName(userPatient.getRealName())
                    .gender(userPatient.getGender())
                    .age(userPatient.getAge())
                    .medicalHistory(userPatient.getMedicalHistory())
                    .phone(userPatient.getPhone())
                    .status(userPatient.getStatus())
                    .gmtCreate(userPatient.getGmtCreate())
                    .build();

            log.info("用户名登录成功：ID {}, 用户名 {}", userPatient.getPatientId(), username);
            return h5LoginVO;

        } catch (Exception e) {
            log.error("用户名登录失败，用户名：{}", username, e);
            return null;
        }
    }


    /**
     * 更新患者/用户个人信息
     */
    @Override
    public Boolean updateH5Patient(UpdateUserCommand command) {
        try {
            if (command.getPatientId() == null) {
                log.warn("更新失败：患者ID不能为空");
                return false;
            }

            // 1. 构建更新实体
            UserPatient updateEntity = UserPatient.builder()
                    .patientId(command.getPatientId())
                    .username(StringUtils.isNotBlank(command.getUsername()) ? command.getUsername() : null)
                    .password(StringUtils.isNotBlank(command.getPassword()) ? command.getPassword() : null)
                    .realName(StringUtils.isNotBlank(command.getRealName()) ? command.getRealName() : null)
                    .gender(command.getGender())
                    .age(command.getAge())
                    .medicalHistory(StringUtils.isNotBlank(command.getMedicalHistory()) ? command.getMedicalHistory() : null)
                    .phone(StringUtils.isNotBlank(command.getPhone()) ? command.getPhone() : null)
                    .status(command.getStatus())
                    .gmtModified(LocalDateTime.now())
                    .build();

            // 2. 执行更新
            boolean updated = this.updateById(updateEntity);
            if (updated) {
                log.info("患者信息更新成功：ID {}", command.getPatientId());
            }
            return updated;

        } catch (Exception e) {
            log.error("更新患者信息失败，患者ID：{}", command.getPatientId(), e);
            return false;
        }
    }

    /**
     * 分页获取所有患者/用户信息
     */
    @Override
    public Page<UserPatient> listPatientInfo(Page<UserPatient> page) {
        try {
            // 按创建时间倒序排列
            QueryWrapper wrapper = QueryWrapper.create()
                    .orderBy(UserPatient::getGmtCreate, false);

            return this.page(page, wrapper);
        } catch (Exception e) {
            log.error("分页查询患者列表失败", e);
            return new Page<>();
        }
    }

    /**
     * 根据ID查询患者/用户信息
     */
    @Override
    public H5LoginVO h5GetPatientInfo(Integer patientId) {
        try {
            if (patientId == null) {
                return null;
            }

            // 1. 查询患者信息
            UserPatient userPatient = this.getById(patientId);
            if (userPatient == null) {
                log.warn("未找到患者信息：ID {}", patientId);
                return null;
            }

            // 2. 转换为VO对象
            H5LoginVO patientInfoVO = H5LoginVO.builder()
                    .patientId(userPatient.getPatientId())
                    .username(userPatient.getUsername())
                    .realName(userPatient.getRealName())
                    .gender(userPatient.getGender())
                    .age(userPatient.getAge())
                    .medicalHistory(userPatient.getMedicalHistory())
                    .phone(userPatient.getPhone())
                    .status(userPatient.getStatus())
                    .gmtCreate(userPatient.getGmtCreate())
                    .build();

            return patientInfoVO;

        } catch (Exception e) {
            log.error("查询患者信息失败，患者ID：{}", patientId, e);
            return null;
        }
    }

    /**
     * 逻辑删除/注销患者/用户账号
     */
    @Override
    public Boolean deleteH5PatientInfo(DeleteUserCommand command) {
        try {
            if (command.getPatientId() == null) {
                log.warn("删除失败：患者ID不能为空");
                return false;
            }

            // 1. 检查患者是否存在
            UserPatient userPatient = this.getById(command.getPatientId());
            if (userPatient == null) {
                log.warn("删除失败：患者ID {} 不存在", command.getPatientId());
                return false;
            }

            // 2. 执行逻辑删除（状态从1变为0）
            UserPatient updateEntity = UserPatient.builder()
                    .patientId(command.getPatientId())
                    .status(0) // 设置为禁用状态
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean deleted = this.updateById(updateEntity);
            if (deleted) {
                log.info("患者账号逻辑删除成功：ID {}, 操作员：{}, 原因：{}",
                        command.getPatientId(), command.getOperatorId(), command.getReason());
            }
            return deleted;

        } catch (Exception e) {
            log.error("删除患者信息失败，患者ID：{}", command.getPatientId(), e);
            return false;
        }
    }

    /**
     * 患者用户创建看病陪诊订单
     */
    @Override
    public Boolean createServiceOrder(CreateOrderDTO orderDTO) {
        try {
            // 1. 验证患者是否存在且状态正常
            UserPatient patient = this.getById(orderDTO.getPatientId());
            if (patient == null || patient.getStatus() == 0) {
                log.warn("创建订单失败：患者不存在或已被禁用，ID={}", orderDTO.getPatientId());
                return false;
            }

            // 2. 生成订单编号（可根据业务规则调整）
            String orderNo = "ORD" + System.currentTimeMillis() + String.format("%04d", orderDTO.getPatientId() % 10000);

            // 3. 创建订单实体
            ServiceOrders order = ServiceOrders.builder()
                    .orderNo(orderNo)
                    .patientId(orderDTO.getPatientId())
                    .patientNameSnapshot(patient.getRealName())
                    .illnessDesc(orderDTO.getIllnessDesc())
                    .hospitalId(orderDTO.getHospitalId())
                    .deptId(orderDTO.getDeptId())
                    .appointmentTime(orderDTO.getAppointmentTime())
                    .estimatedDuration(orderDTO.getEstimatedDuration())
                    .status(0) // 待接单状态
                    .price(orderDTO.getPrice())
                    .gmtCreate(LocalDateTime.now())
                    .gmtModified(LocalDateTime.now())
                    .build();

            // 4. 保存订单
            boolean saved = serviceOrdersMapper.insert(order) > 0;
            if (saved) {
                log.info("患者创建订单成功：患者ID={}, 订单ID={}, 订单号={}",
                        orderDTO.getPatientId(), order.getOrderId(), orderNo);
            }
            return saved;

        } catch (Exception e) {
            log.error("创建订单失败：患者ID={}", orderDTO.getPatientId(), e);
            return false;
        }
    }

    /**
     * 患者用户查看自己的所有历史订单
     */
    @Override
    public Page<ServiceOrders> listPatientOrders(Integer patientId, Integer pageNum, Integer pageSize) {
        try {
            if (patientId == null) {
                return new Page<>();
            }

            Page<ServiceOrders> page = new Page<>(pageNum, pageSize);
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .eq(ServiceOrders::getPatientId, patientId)
                    .orderBy(ServiceOrders::getGmtCreate, false);

            return serviceOrdersMapper.paginate(page, queryWrapper);

        } catch (Exception e) {
            log.error("查询患者订单列表失败：患者ID={}", patientId, e);
            return new Page<>();
        }
    }

    /**
     * 订单结束后用户对相关订单的陪诊员打分和做出评价
     */
    @Override
    public Boolean submitServiceEvaluation(ServiceEvaluationDTO evaluationDTO) {
        try {
            // 1. 验证订单是否存在且属于该患者
            ServiceOrders order = serviceOrdersMapper.selectOneById(evaluationDTO.getOrderId());
            if (order == null || !order.getPatientId().equals(evaluationDTO.getPatientId())) {
                log.warn("提交评价失败：订单不存在或不属于该患者，订单ID={}, 患者ID={}",
                        evaluationDTO.getOrderId(), evaluationDTO.getPatientId());
                return false;
            }

            // 2. 验证订单状态是否为已完成（状态2）
            if (order.getStatus() != 2) {
                log.warn("提交评价失败：订单未完成，订单ID={}, 状态={}", evaluationDTO.getOrderId(), order.getStatus());
                return false;
            }

            // 3. 检查是否已评价过
            QueryWrapper checkWrapper = QueryWrapper.create()
                    .eq(ServiceEvaluation::getOrderId, evaluationDTO.getOrderId());
            ServiceEvaluation existingEval = serviceEvaluationMapper.selectOneByQuery(checkWrapper);
            if (existingEval != null) {
                log.warn("提交评价失败：该订单已评价过，订单ID={}", evaluationDTO.getOrderId());
                return false;
            }

            // 4. 创建评价记录
            ServiceEvaluation evaluation = ServiceEvaluation.builder()
                    .orderId(evaluationDTO.getOrderId())
                    .patientId(evaluationDTO.getPatientId())
                    .escortId(evaluationDTO.getEscortId())
                    .score(evaluationDTO.getScore())
                    .comment(evaluationDTO.getComment())
                    .gmtCreate(LocalDateTime.now())
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean saved = serviceEvaluationMapper.insert(evaluation) > 0;
            if (saved) {
                log.info("提交评价成功：订单ID={}, 患者ID={}, 评分={}",
                        evaluationDTO.getOrderId(), evaluationDTO.getPatientId(), evaluationDTO.getScore());
            }
            return saved;

        } catch (Exception e) {
            log.error("提交评价失败：订单ID={}", evaluationDTO.getOrderId(), e);
            return false;
        }
    }

    /**
     * 用户患者对相关订单的陪诊员进行投诉
     */
    @Override
    public Boolean submitServiceComplaint(ServiceComplaintDTO complaintDTO) {
        try {
            // 1. 验证订单是否存在且属于该投诉人
            ServiceOrders order = serviceOrdersMapper.selectOneById(complaintDTO.getOrderId());
            if (order == null || !order.getPatientId().equals(complaintDTO.getComplainantId())) {
                log.warn("提交投诉失败：订单不存在或不属于该投诉人，订单ID={}, 投诉人ID={}",
                        complaintDTO.getOrderId(), complaintDTO.getComplainantId());
                return false;
            }

            // 2. 检查是否已投诉过
            QueryWrapper checkWrapper = QueryWrapper.create()
                    .eq(ServiceComplaint::getOrderId, complaintDTO.getOrderId());
            ServiceComplaint existingComplaint = serviceComplaintMapper.selectOneByQuery(checkWrapper);
            if (existingComplaint != null) {
                log.warn("提交投诉失败：该订单已存在投诉，订单ID={}", complaintDTO.getOrderId());
                return false;
            }

            // 3. 更新订单状态为投诉中（状态4）
            ServiceOrders updateOrder = ServiceOrders.builder()
                    .orderId(complaintDTO.getOrderId())
                    .status(4) // 投诉中状态
                    .gmtModified(LocalDateTime.now())
                    .build();
            serviceOrdersMapper.update(updateOrder);

            // 4. 创建投诉记录
            ServiceComplaint complaint = ServiceComplaint.builder()
                    .orderId(complaintDTO.getOrderId())
                    .complainantId(complaintDTO.getComplainantId())
                    .respondentId(complaintDTO.getRespondentId())
                    .content(complaintDTO.getContent())
                    .status(0) // 待处理状态
                    .gmtCreate(LocalDateTime.now())
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean saved = serviceComplaintMapper.insert(complaint) > 0;
            if (saved) {
                log.info("提交投诉成功：订单ID={}, 投诉人ID={}",
                        complaintDTO.getOrderId(), complaintDTO.getComplainantId());
            }
            return saved;

        } catch (Exception e) {
            log.error("提交投诉失败：订单ID={}", complaintDTO.getOrderId(), e);
            return false;
        }
    }

    /**
     * 查看订单状态（是否被接单）
     */
    @Override
    public OrderStatusVO checkOrderStatus(Long orderId) {
        try {
            ServiceOrders order = serviceOrdersMapper.selectOneById(orderId);
            if (order == null) {
                log.warn("查询订单状态失败：订单不存在，订单ID={}", orderId);
                return null;
            }

            // 状态描述映射
            String statusDesc = switch (order.getStatus()) {
                case 0 -> "待接单";
                case 1 -> "已接单（进行中）";
                case 2 -> "已完成";
                case 3 -> "已取消";
                case 4 -> "异常/投诉中";
                default -> "未知状态";
            };


            OrderStatusVO statusVO = new OrderStatusVO();
            statusVO.setOrderId(order.getOrderId());
            statusVO.setOrderNo(order.getOrderNo());
            statusVO.setStatus(order.getStatus());
            statusVO.setStatusDesc(statusDesc);
            statusVO.setEscortId(order.getEscortId());
            statusVO.setPatientNameSnapshot(order.getPatientNameSnapshot());
            statusVO.setAppointmentTime(order.getAppointmentTime());
            statusVO.setPrice(order.getPrice());
            statusVO.setGmtCreate(order.getGmtCreate());

            return statusVO;

        } catch (Exception e) {
            log.error("查询订单状态失败：订单ID={}", orderId, e);
            return null;
        }
    }

    @Override
    public Page<ServiceOrders> getOrdersByStatus(Integer patientId, Integer status, Integer pageNum, Integer pageSize) {
        try {
            Page<ServiceOrders> page = new Page<>(pageNum, pageSize);
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .eq(ServiceOrders::getPatientId, patientId)
                    .eq(ServiceOrders::getStatus, status)
                    .orderBy(ServiceOrders::getGmtCreate, false);

            return serviceOrdersMapper.paginate(page, queryWrapper);
        } catch (Exception e) {
            log.error("按状态查询订单失败：患者ID={}, 状态={}", patientId, status, e);
            return new Page<>();
        }
    }

    /**
     * 更新订单信息
     *
     * @param orderId
     * @param patientId
     * @param updateOrderDTO
     */
    @Override
    public Boolean updateOrderInfo(Long orderId, Integer patientId, UpdateOrderDTO updateOrderDTO) {
        try {
            // 1. 验证订单是否存在且属于该患者
            ServiceOrders order = getOrderByIdAndPatientId(orderId, patientId);
            if (order == null) {
                log.warn("更新订单失败：订单不存在或不属于该患者，订单ID={}, 患者ID={}", orderId, patientId);
                return false;
            }

            // 2. 只有待接单状态（0）的订单可以更新
            if (order.getStatus() != 0) {
                log.warn("更新订单失败：订单状态不允许更新，订单ID={}, 当前状态={}", orderId, order.getStatus());
                return false;
            }

            // 3. 构建更新实体
            ServiceOrders updateEntity = ServiceOrders.builder()
                    .orderId(orderId)
                    .illnessDesc(updateOrderDTO.getIllnessDesc() != null ?
                            updateOrderDTO.getIllnessDesc() : order.getIllnessDesc())
                    .appointmentTime(updateOrderDTO.getAppointmentTime() != null ?
                            updateOrderDTO.getAppointmentTime() : order.getAppointmentTime())
                    .estimatedDuration(updateOrderDTO.getEstimatedDuration() != null ?
                            updateOrderDTO.getEstimatedDuration() : order.getEstimatedDuration())
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean updated = serviceOrdersMapper.update(updateEntity) > 0;
            if (updated) {
                log.info("更新订单信息成功：订单ID={}, 患者ID={}", orderId, patientId);
            }
            return updated;
        } catch (Exception e) {
            log.error("更新订单信息失败：订单ID={}, 患者ID={}", orderId, patientId, e);
            return false;
        }
    }

    /**
     * 根据订单ID和患者ID获取订单（验证权限）
     *
     * @param orderId
     * @param patientId
     */
    @Override
    public ServiceOrders getOrderByIdAndPatientId(Long orderId, Integer patientId) {
        try {
            if (orderId == null || patientId == null) {
                log.warn("查询订单详情失败：订单ID或患者ID为空");
                return null;
            }

            // 使用 QueryWrapper 构建查询条件
            QueryWrapper queryWrapper = QueryWrapper.create()
                    .eq(ServiceOrders::getOrderId, orderId)
                    .eq(ServiceOrders::getPatientId, patientId);

            ServiceOrders order = serviceOrdersMapper.selectOneByQuery(queryWrapper);
            if (order == null) {
                log.warn("查询订单详情失败：订单不存在或无权限，订单ID={}, 患者ID={}", orderId, patientId);
            }
            return order;

        } catch (Exception e) {
            log.error("查询订单详情失败，订单ID={}, 患者ID={}", orderId, patientId, e);
            return null;
        }
    }

    /**
     * 患者取消订单
     *
     * @param orderId   订单ID
     * @param patientId 患者ID（用于权限验证）
     * @return 是否取消成功
     */
    @Override
    public Boolean cancelOrder(Long orderId, Integer patientId) {
        try {
            if (orderId == null || patientId == null) {
                log.warn("取消订单失败：订单ID或患者ID为空");
                return false;
            }

            // 1. 验证订单是否存在且属于该患者
            ServiceOrders order = getOrderByIdAndPatientId(orderId, patientId);
            if (order == null) {
                log.warn("取消订单失败：订单不存在或不属于该患者，订单ID={}, 患者ID={}", orderId, patientId);
                return false;
            }

            // 2. 只有待接单状态（0）的订单可以取消
            if (order.getStatus() != 0) {
                log.warn("取消订单失败：订单状态不允许取消，订单ID={}, 当前状态={}", orderId, order.getStatus());
                return false;
            }

            // 3. 更新订单状态为已取消（3）
            ServiceOrders updateOrder = ServiceOrders.builder()
                    .orderId(orderId)
                    .status(3) // 已取消状态
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean updated = serviceOrdersMapper.update(updateOrder) > 0;
            if (updated) {
                log.info("患者取消订单成功：订单ID={}, 患者ID={}", orderId, patientId);
            }
            return updated;

        } catch (Exception e) {
            log.error("取消订单失败：订单ID={}, 患者ID={}", orderId, patientId, e);
            return false;
        }
    }


}
