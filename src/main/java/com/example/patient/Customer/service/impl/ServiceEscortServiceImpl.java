package com.example.patient.Customer.service.impl;

import com.example.patient.DTO.Command.RegisterEscorCommand;
import com.example.patient.DTO.Command.UpdateEscorCommand;
import com.example.patient.DTO.VO.EscorLoginVO;
import com.example.patient.DTO.VO.H5LoginVO;
import com.example.patient.H5.mapper.UserPatientMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.ServiceEscort;
import com.example.patient.Customer.mapper.ServiceEscortMapper;
import com.example.patient.Customer.service.ServiceEscortService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.patient.H5.entity.UserPatient;

import com.mybatisflex.core.query.QueryWrapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * 陪诊员信息表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Slf4j
@Service
public class ServiceEscortServiceImpl extends ServiceImpl<ServiceEscortMapper, ServiceEscort>
        implements ServiceEscortService{

    @Resource
    private ServiceEscortMapper serviceEscortMapper;

    @Override
    public Boolean registerEscort(RegisterEscorCommand command) {
        // 检查手机号是否已注册
        QueryWrapper checkWrapper = QueryWrapper.create()
                .eq("username", command.getUsername());
        ServiceEscort existing = mapper.selectOneByQuery(checkWrapper);
        if (existing != null) {
            log.warn("手机号已注册: {}", command.getUsername());
            return false;
        }

        ServiceEscort escort = ServiceEscort.builder()
                .username(command.getUsername())
                .password(command.getPassword()) // 前端应已加密
                .realName(command.getRealName())
                .phone(command.getPhone())
                .idCardImg(command.getIdCardImg())
                .healthCertImg(command.getHealthCertImg())
                .trainingCertImg(command.getTrainingCertImg())
                .auditStatus(0) // 注册后默认为待审核
                .accountStatus(1) // 账号状态正常
                .creditScore(100) // 默认信用分
                .starRating(BigDecimal.valueOf(5.0)) // 默认星级
                .gmtCreate(LocalDateTime.now())
                .gmtModified(LocalDateTime.now())
                .build();

        return mapper.insert(escort) > 0;
    }

    @Override
    public EscorLoginVO loginEscortPhone(String phone, String password) {
        try {
            // 使用StringUtils.isBlank检查空值
            if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) {
                log.warn("手机号或密码不能为空");
                return null;
            }

            // 使用数据库列名（snake_case）而不是Java字段名（camelCase）
            QueryWrapper wrapper = QueryWrapper.create()
                    .eq("phone", phone)
                    .eq("password", password)
                    .eq("audit_status", 1)  // 数据库列名：audit_status
                    .eq("account_status", 1); // 数据库列名：account_status

            ServiceEscort serviceEscort = serviceEscortMapper.selectOneByQuery(wrapper);
            if (serviceEscort == null) {
                log.warn("手机号登录失败：手机号 {} 不存在或密码错误，或账号未审核/已停用", phone);
                return null;
            }

            // 构建返回的VO对象
            EscorLoginVO escorLoginVO = EscorLoginVO.builder()
                    .escortId(serviceEscort.getEscortId())
                    .username(serviceEscort.getUsername())
                    .realName(serviceEscort.getRealName())
                    .phone(serviceEscort.getPhone())
                    .auditStatus(serviceEscort.getAuditStatus())
                    .accountStatus(serviceEscort.getAccountStatus())
                    .creditScore(serviceEscort.getCreditScore())
                    .starRating(serviceEscort.getStarRating())
                    .build();

            log.info("手机号登录成功：ID {}, 手机号 {}", serviceEscort.getEscortId(), phone);
            return escorLoginVO;

        } catch (Exception e) {
            log.error("手机号登录失败，手机号：{}", phone, e);
            return null;
        }
    }

    @Override
    public EscorLoginVO loginEscortname(String username, String password) {
        try {
            // 使用StringUtils.isBlank检查空值
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                log.warn("用户名或密码不能为空");
                return null;
            }

            // 使用数据库列名（snake_case）而不是Java字段名（camelCase）
            QueryWrapper wrapper = QueryWrapper.create()
                    .eq("username", username)
                    .eq("password", password)
                    .eq("audit_status", 1)  // 数据库列名：audit_status
                    .eq("account_status", 1); // 数据库列名：account_status

            ServiceEscort serviceEscort = serviceEscortMapper.selectOneByQuery(wrapper);
            if (serviceEscort == null) {
                log.warn("账号登录失败：账号 {} 不存在或密码错误，或账号未审核/已停用", username);
                return null;
            }

            // 构建返回的VO对象
            EscorLoginVO escorLoginVO = EscorLoginVO.builder()
                    .escortId(serviceEscort.getEscortId())
                    .username(serviceEscort.getUsername())
                    .realName(serviceEscort.getRealName())
                    .phone(serviceEscort.getPhone())
                    .auditStatus(serviceEscort.getAuditStatus())
                    .accountStatus(serviceEscort.getAccountStatus())
                    .creditScore(serviceEscort.getCreditScore())
                    .starRating(serviceEscort.getStarRating())
                    .build();

            log.info("账号登录成功：ID {}, 账号 {}", serviceEscort.getEscortId(), username);
            return escorLoginVO;

        } catch (Exception e) {
            log.error("账号登录失败，账号：{}", username, e);
            return null;
        }
    }




    @Override
    public Boolean updateEscortInfo(UpdateEscorCommand command) {
        // 先查询是否存在该陪诊员
        ServiceEscort existing = mapper.selectOneById(command.getEscortId());
        if (existing == null) {
            log.warn("陪诊员不存在: {}", command.getEscortId());
            return false;
        }

        // 构建更新对象 - 只更新非空字段
        ServiceEscort escort = ServiceEscort.builder()
                .escortId(command.getEscortId())
                .build();

        // 逐个字段判断并设置
        if (StringUtils.isNotBlank(command.getPassword())) {
            escort.setPassword(command.getPassword());
        }
        if (StringUtils.isNotBlank(command.getRealName())) {
            escort.setRealName(command.getRealName());
        }
        if (StringUtils.isNotBlank(command.getPhone())) {
            escort.setPhone(command.getPhone());
        }
        if (StringUtils.isNotBlank(command.getIdCardImg())) {
            escort.setIdCardImg(command.getIdCardImg());
        }
        if (StringUtils.isNotBlank(command.getHealthCertImg())) {
            escort.setHealthCertImg(command.getHealthCertImg());
        }
        if (StringUtils.isNotBlank(command.getTrainingCertImg())) {
            escort.setTrainingCertImg(command.getTrainingCertImg());
        }

        // 设置更新时间
        escort.setGmtModified(LocalDateTime.now());

        return mapper.update(escort) > 0;
    }

    @Override
    public Page<ServiceEscort> listEscortInfo(Page<ServiceEscort> page) {
        // 无条件分页查询，按创建时间倒序
        QueryWrapper wrapper = QueryWrapper.create()
                .orderBy("gmt_create", false);

        return mapper.paginate(page, wrapper);
    }

    @Override
    public EscorLoginVO getEscortInfo(Integer escortId) {
        ServiceEscort escort = mapper.selectOneById(escortId);
        if (escort == null) {
            log.warn("陪诊员不存在: {}", escortId);
            return null;
        }

        return EscorLoginVO.builder()
                .escortId(escort.getEscortId())
                .username(escort.getUsername())
                .realName(escort.getRealName())
                .phone(escort.getPhone())
                .auditStatus(escort.getAuditStatus())
                .accountStatus(escort.getAccountStatus())
                .creditScore(escort.getCreditScore())
                .starRating(escort.getStarRating())
                .build();
    }

}
