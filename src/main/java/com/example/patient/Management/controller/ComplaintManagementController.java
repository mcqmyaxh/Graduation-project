package com.example.patient.Management.controller;

import com.example.patient.Customer.service.ServiceComplaintService;
import com.example.patient.DTO.VO.ComplaintVO;
import com.example.patient.util.exp.ResultData;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/Platform")
@Tag(name = "订单投诉信息接口", description = "订单投诉信息管理操作接口")
public class ComplaintManagementController {

    @Resource
    private ServiceComplaintService serviceComplaintService;

    /**
     * 处理投诉
     */
    @PutMapping("/complaint/handle")
    @Operation(summary = "处理投诉", description = "status=1 通过并拉黑被投诉人，status=2 驳回")
    @PreAuthorize("@ss.hasPermission('handleComplaint')")
    public ResultData<Boolean> handleComplaint(@RequestParam Long complaintId,
                                               @RequestParam Integer handleStatus,
                                               @RequestParam(required = false) String resultNote) {
        boolean ok = serviceComplaintService.handleComplaint(complaintId, handleStatus, resultNote);
        return ok ? ResultData.success(true, "处理成功") : ResultData.failed("处理失败");
    }

    /**
     * 分页查询所有投诉
     */
    @GetMapping("/complaints")
    @Operation(summary = "分页查询所有投诉", description = "status 不传则查询全部")
    @PreAuthorize("@ss.hasPermission('listAllComplaints')")
    public ResultData<Page<ComplaintVO>> listAllComplaints(
            @RequestParam(defaultValue = "1")  @Parameter(description = "页码")  Integer pageNum,
            @RequestParam(defaultValue = "10") @Parameter(description = "每页条数") Integer pageSize,
            @RequestParam(required = false)    @Parameter(description = "状态筛选：0待处理 1已处理 2已驳回") Integer status) {
        Page<ComplaintVO> page = serviceComplaintService.listAllComplaints(pageNum, pageSize, status);
        return ResultData.success(page);
    }
}
