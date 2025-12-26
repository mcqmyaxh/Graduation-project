package com.example.patient.Customer.service;

import com.example.patient.DTO.Command.*;
import com.example.patient.DTO.VO.EscorLoginVO;

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

}
