package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.model.ServiceApplication;
import com.elecredit.service.BaseService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ServiceApplicationService extends BaseService<ServiceApplication> {
    /**
     * 根据用户ID和服务ID获取申请记录
     * @param userId
     * @param serviceId
     * @return
     */
    List<ServiceApplication> getByUserAndService(Long userId, Long serviceId);

    ServiceApplication getLastByUserAndService(Long userId, Long serviceId);

    /**
     * 分页查询
     * @param condition
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ServiceApplication> paginate(JSONObject condition, int pageNum, int pageSize);

    /**
     * 统计条数
     * @param condition
     * @return
     */
    long count(JSONObject condition);

    /**
     * 审核服务申请
     * @param applicationId
     * @param status
     * @param operatorId
     * @param operatorName
     * @param remark
     */
    ServiceApplication check(Long applicationId, int status, Long operatorId, String operatorName, String remark);
}
