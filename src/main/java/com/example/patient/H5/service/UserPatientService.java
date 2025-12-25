package com.example.patient.H5.service;

import com.example.patient.DTO.Command.DeleteUserCommand;
import com.example.patient.DTO.Command.RegisterUserCommand;
import com.example.patient.DTO.Command.UpdateUserCommand;
import com.example.patient.DTO.VO.H5LoginVO;
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
}
