package com.example.patient.Management.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.example.patient.Management.entity.BaseHospital;

/**
 * 医院信息表 服务层。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
public interface BaseHospitalService extends IService<BaseHospital> {
    /**
     * 分页查询医院
     * @param page 分页对象
     * @param name 医院名称（模糊查询）
     * @return 分页结果
     */
    Page<BaseHospital> pageHospital(Integer pageNumber, Integer pageSize, String name);

    /**
     * 修改医院状态
     * @param hospitalId 医院ID
     * @param status 状态 1:启用 0:停用
     * @return 是否成功
     */
    boolean updateHospitalStatus(Integer hospitalId, Integer status);

    BaseHospital getHospitalById(Integer hospitalId);
}
