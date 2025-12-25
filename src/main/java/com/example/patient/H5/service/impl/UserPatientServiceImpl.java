package com.example.patient.H5.service.impl;

import com.example.patient.DTO.Command.DeleteUserCommand;
import com.example.patient.DTO.Command.RegisterUserCommand;
import com.example.patient.DTO.Command.UpdateUserCommand;
import com.example.patient.DTO.VO.H5LoginVO;
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
}
