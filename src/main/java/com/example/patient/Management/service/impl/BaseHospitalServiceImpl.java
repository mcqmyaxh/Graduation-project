package com.example.patient.Management.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Management.entity.BaseHospital;
import com.example.patient.Management.mapper.BaseHospitalMapper;
import com.example.patient.Management.service.BaseHospitalService;
import org.springframework.stereotype.Service;

/**
 * 医院信息表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class BaseHospitalServiceImpl extends ServiceImpl<BaseHospitalMapper, BaseHospital>  implements BaseHospitalService{


    // 分页查询医院
    @Override
    public Page<BaseHospital> pageHospital(Integer pageNumber, Integer pageSize, String name) {
        return queryChain()
                .like(BaseHospital::getName, name) // 支持按名称模糊搜索
                .orderBy(BaseHospital::getHospitalId, false) // 降序排列
                .page(new Page<>(pageNumber, pageSize));
    }

    // 停用/启用医院 (状态修改)
    @Override
    public boolean updateHospitalStatus(Integer hospitalId, Integer status) {
        return updateChain()
                .set(BaseHospital::getStatus, status)
                .where(BaseHospital::getHospitalId).eq(hospitalId)
                .update();
    }

    @Override
    public BaseHospital getHospitalById(Integer hospitalId) {
        return this.getById(hospitalId); // 调用父类 ServiceImpl 的 getById
    }
}
