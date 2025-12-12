package com.example.patient.Customer.service;

import com.example.patient.DTO.Command.RegisterUserCommand;
import com.example.patient.DTO.Command.UpdateUserCommand;
import com.mybatisflex.core.service.IService;
import com.example.patient.Customer.entity.UserPatient;

import java.util.List;

/**
 * 患者/家属信息表 服务层。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
public interface UserPatientService extends IService<UserPatient> {
    UserPatient registerUser(RegisterUserCommand request);

    Boolean deleteUser(Long UserId);

    Boolean updateUser(UpdateUserCommand command);

    UserPatient getUser(UserPatient userPatient);

    List<UserPatient> listUsers(UserPatient userPatient);

    UserPatient loginUser(String username, String password);
}
