package com.example.patient.Management.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Management.entity.BaseHospitalDeptMapping;
import com.example.patient.Management.mapper.BaseHospitalDeptMappingMapper;
import com.example.patient.Management.service.BaseHospitalDeptMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 医院-科室号源映射表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-12-07
 */
@Service
public class BaseHospitalDeptMappingServiceImpl extends ServiceImpl<BaseHospitalDeptMappingMapper, BaseHospitalDeptMapping>
        implements BaseHospitalDeptMappingService{

    /**
     * 配置科室号源
     * 如果存在则更新，不存在则新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean configDepartmentSlots(BaseHospitalDeptMapping mapping) {
        // 先查询是否存在
        BaseHospitalDeptMapping exists = queryChain()
                .where(BaseHospitalDeptMapping::getHospitalId).eq(mapping.getHospitalId())
                .and(BaseHospitalDeptMapping::getDeptId).eq(mapping.getDeptId())
                .one();

        if (exists != null) {
            // 更新操作
            mapping.setMappingId(exists.getMappingId());
            return updateById(mapping);
        } else {
            // 新增操作
            return save(mapping);
        }
    }

    @Override
    public BaseHospitalDeptMapping getDepartmentMapping(Integer hospitalId, Integer deptId) {
        return queryChain()
                // 使用 Lambda 写法
                .where(BaseHospitalDeptMapping::getHospitalId).eq(hospitalId)
                .and(BaseHospitalDeptMapping::getDeptId).eq(deptId)
                .one();
    }

    @Override
    public List<BaseHospitalDeptMapping> listByHospitalId(Integer hospitalId) {
        return queryChain()
                .where(BaseHospitalDeptMapping::getHospitalId).eq(hospitalId)
                .list();
    }

    @Override
    public Page<BaseHospitalDeptMapping> pageMapping(Integer pageNumber, Integer pageSize, Integer hospitalId) {
        return queryChain()
                .where(BaseHospitalDeptMapping::getHospitalId).eq(hospitalId)
                .page(new Page<>(pageNumber, pageSize));
    }


}
