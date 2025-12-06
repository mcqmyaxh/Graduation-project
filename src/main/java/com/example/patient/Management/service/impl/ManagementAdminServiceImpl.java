package com.example.patient.Management.service.impl;

import com.example.patient.DTO.Command.UpdateAdminCommand;
import com.example.patient.registry.infrastructure.common.exception.ApiException;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.processor.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Management.entity.ManagementAdmin;
import com.example.patient.Management.mapper.ManagementAdminMapper;
import com.example.patient.Management.service.ManagementAdminService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;





import java.time.LocalDateTime;
import java.util.List;
/**
 * 平台管理员表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class ManagementAdminServiceImpl extends ServiceImpl<ManagementAdminMapper, ManagementAdmin>  implements ManagementAdminService{
    @Resource
    private ManagementAdminMapper managementAdminMapper;


    @Override
    public Boolean addAdmin(ManagementAdmin managementAdmin) {
        if (managementAdmin == null
                || StrUtil.isBlank(managementAdmin.getUsername())
                || StrUtil.isBlank(managementAdmin.getPassword())
                || StrUtil.isBlank(managementAdmin.getRealName())
                || StrUtil.isBlank(managementAdmin.getPhone())) {
            return false;
        }

        // 唯一性校验
        boolean usernameExists = this.count(QueryWrapper.create()
                .eq(ManagementAdmin::getUsername, managementAdmin.getUsername())) > 0;
        boolean phoneExists = this.count(QueryWrapper.create()
                .eq(ManagementAdmin::getPhone, managementAdmin.getPhone())) > 0;

        if (usernameExists || phoneExists) {
            return false;
        }

        // 默认值

        managementAdmin.setGmtCreate(LocalDateTime.now());
        managementAdmin.setGmtModified(LocalDateTime.now());

        return managementAdminMapper.insert(managementAdmin) > 0;
    }

    @Override
    public Boolean deleteAdmin(Long adminId) {
        return LogicDeleteManager.execWithoutLogicDelete(()->
                managementAdminMapper.deleteById(adminId)
        ) >0;
    }

    @Override
    public Boolean updateAdmin(UpdateAdminCommand command) {


        ManagementAdmin admin = ManagementAdmin.builder()
                .id(command.getId())
                .username(command.getUsername())
                .password(command.getPassword())
                .phone(command.getPhone())
                .build();
        return managementAdminMapper.update(admin) > 0;
    }


    @Override
    public ManagementAdmin getAdmin(ManagementAdmin managementAdmin) {
        QueryWrapper wrapper =QueryWrapper.create()
                .eq("id",managementAdmin.getId())
                .eq("username",managementAdmin.getUsername())
                .eq("phone",managementAdmin.getPhone());
        return managementAdminMapper.selectOneByQuery(wrapper);
    }

    @Override
    public List<ManagementAdmin> listAdmins(ManagementAdmin managementAdmin) {
        QueryWrapper wrapper = QueryWrapper.create()
                .eq("real_name",managementAdmin.getRealName())

                .between("gmt_create",managementAdmin.getGmtCreate(),managementAdmin.getGmtModified());
        return managementAdminMapper.selectListByQuery(wrapper);
    }

    @Override
    public ManagementAdmin loginByPhoneAndPassword(String phone, String password) {
        if (StrUtil.isBlank(phone) || StrUtil.isBlank(password)) {
            return null;
        }
        // 1. 查手机号
        ManagementAdmin admin = this.mapper.selectOneByQuery(
                QueryWrapper.create().eq(ManagementAdmin::getPhone, phone));
        if (admin == null) {
            return null;
        }
        // 2. 直接比较明文密码（取消BCrypt加密验证）
        String dbPassword = admin.getPassword();
        boolean match = password.equals(dbPassword);
        if (!match) {
            return null;
        }
        // 3. 脱敏后返回
        admin.setPassword(null);
        return admin;
    }
}