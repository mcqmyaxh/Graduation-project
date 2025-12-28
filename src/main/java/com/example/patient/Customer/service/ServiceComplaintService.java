package com.example.patient.Customer.service;

import com.example.patient.DTO.VO.ComplaintVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.example.patient.Customer.entity.ServiceComplaint;

/**
 * 投诉管理表 服务层。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
public interface ServiceComplaintService extends IService<ServiceComplaint> {
    /**
     * 处理投诉
     */
    Boolean handleComplaint(Long complaintId, Integer handleStatus, String resultNote);

    /**
     * 查看所有投诉
     */
    Page<ComplaintVO> listAllComplaints(Integer pageNum, Integer pageSize, Integer status);
}
