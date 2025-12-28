package com.example.patient.Customer.service.impl;

import com.example.patient.Customer.entity.ServiceEscort;
import com.example.patient.Customer.mapper.ServiceEscortMapper;
import com.example.patient.Customer.mapper.ServiceOrdersMapper;
import com.example.patient.DTO.VO.ComplaintVO;
import com.example.patient.H5.mapper.UserPatientMapper;
import com.example.patient.Management.mapper.BaseDepartmentMapper;
import com.example.patient.Management.mapper.BaseHospitalMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.ServiceComplaint;
import com.example.patient.Customer.mapper.ServiceComplaintMapper;
import com.example.patient.Customer.service.ServiceComplaintService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 投诉管理表 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */

@Slf4j
@Service
public class ServiceComplaintServiceImpl extends ServiceImpl<ServiceComplaintMapper, ServiceComplaint>
        implements ServiceComplaintService{

    @Resource
    private ServiceEscortMapper serviceEscortMapper;

    @Resource
    private ServiceOrdersMapper serviceOrdersMapper;


    @Resource
    private ServiceComplaintMapper serviceComplaintMapper;


    @Resource
    private UserPatientMapper userPatientMapper;

    @Resource
    private BaseHospitalMapper baseHospitalMapper;

    @Resource
    private BaseDepartmentMapper baseDepartmentMapper;


    /**
     * 处理投诉
     *
     * @param complaintId
     * @param handleStatus
     * @param resultNote
     */
    @Override
    public Boolean handleComplaint(Long complaintId, Integer handleStatus, String resultNote) {
        try {
            if (complaintId == null || handleStatus == null) {
                return false;
            }

            ServiceComplaint complaint = serviceComplaintMapper.selectOneById(complaintId);
            if (complaint == null) {
                return false;
            }

            // 更新投诉状态
            ServiceComplaint updateComplaint = ServiceComplaint.builder()
                    .complaintId(complaintId)
                    .status(handleStatus)
                    .resultNote(resultNote)
                    .handledAt(LocalDateTime.now())
                    .gmtModified(LocalDateTime.now())
                    .build();

            boolean updated = serviceComplaintMapper.update(updateComplaint) > 0;

            if (updated && handleStatus == 1) {
                // 如果投诉已处理，需要取消被投诉陪诊员的接单资格
                Integer escortId = complaint.getRespondentId();
                ServiceEscort updateEscort = ServiceEscort.builder()
                        .escortId(escortId)
                        .accountStatus(3) // 封号/黑名单
                        .gmtModified(LocalDateTime.now())
                        .build();
                serviceEscortMapper.update(updateEscort);

                log.info("投诉处理完成，陪诊员被取消资格：投诉ID={}, 陪诊员ID={}", complaintId, escortId);
            }

            return updated;

        } catch (Exception e) {
            log.error("处理投诉失败：投诉ID={}", complaintId, e);
            return false;
        }
    }

    /**
     * 查看所有投诉
     *
     * @param pageNum
     * @param pageSize
     * @param status
     */
    @Override
    public Page<ComplaintVO> listAllComplaints(Integer pageNum, Integer pageSize, Integer status) {
        try {
            Page<ComplaintVO> page = new Page<>(pageNum, pageSize);

            QueryWrapper queryWrapper = QueryWrapper.create()
                    .select("sc.*")
                    .select("up.real_name AS complainant_name")
                    .select("se.real_name AS respondent_name")
                    .from("service_complaint sc")
                    .leftJoin("user_patient up").on("sc.complainant_id = up.patient_id")
                    .leftJoin("service_escort se").on("sc.respondent_id = se.escort_id");

            if (status != null) {
                queryWrapper.where("sc.status = ?", status);
            }

            queryWrapper.orderBy("sc.gmt_create", false);

            return serviceComplaintMapper.paginateAs(page, queryWrapper, ComplaintVO.class);

        } catch (Exception e) {
            log.error("查询投诉列表失败", e);
            return new Page<>();
        }
    }
}
