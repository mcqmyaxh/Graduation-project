package com.example.patient.Customer.service.impl;

import com.example.patient.DTO.Command.RegisterUserCommand;
import com.example.patient.DTO.Command.UpdateUserCommand;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.UserPatient;
import com.example.patient.Customer.mapper.UserPatientMapper;
import com.example.patient.Customer.service.UserPatientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 患者/家属信息表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class UserPatientServiceImpl extends ServiceImpl<UserPatientMapper, UserPatient>  implements UserPatientService{


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserPatient registerUser(RegisterUserCommand request) {
        String username;
        return null;
    }

    @Override
    public Boolean deleteUser(Long UserId) {
        return null;
    }

    @Override
    public Boolean updateUser(UpdateUserCommand command) {
        return null;
    }

    @Override
    public UserPatient getUser(UserPatient userPatient) {
        return null;
    }

    @Override
    public List<UserPatient> listUsers(UserPatient userPatient) {
        return null;
    }

    @Override
    public UserPatient loginUser(String username, String password) {
        return null;
    }
}
