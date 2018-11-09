package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.model.ServiceApplication;
import com.elecredit.op.model.ServiceRequestRecord;
import com.elecredit.service.BaseService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ServiceRequestRecordService extends BaseService<ServiceApplication> {
    /**
     * 根据请求流水号查找
     * @param requestSn
     * @return
     */
    ServiceRequestRecord getByRequestSn(String requestSn);

    /**
     * 根据客户请求流水号查找
     * @param requestSn
     * @return
     */
    ServiceRequestRecord getByClientRequestSn(String requestSn);
    /**
     * 分页查询
     * @param condition
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ServiceRequestRecord> paginate(JSONObject condition, int pageNum, int pageSize);

    /**
     * 统计条数
     * @param condition
     * @return
     */
    long count(JSONObject condition);
}
