package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.model.OpenApi;
import com.elecredit.service.BaseService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OpenApiService extends BaseService<OpenApi> {


    /**
     * 根据实体Id查询OpenApi
     * @param entityId
     * @return
     */
    List<OpenApi> getByEntity(Long entityId);

    /**
     * 获取第N页的OpenApi列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<OpenApi> getByPage(JSONObject condition, int pageNum, int pageSize);
    /**
     * 返回记录总数
     * @return
     */
    Long count(JSONObject condition);

    /**
     * 更新OpenApi状态
     * @param appId
     * @param enabled
     * @return
     */
    void updateAppStatus(Long appId,boolean enabled);

    /**
     * 获取客户OpenAPI
     * @param customerId
     * @return
     */
    List<OpenApi> getByCustomer(Long customerId);
    /**
     * 根据客户删OpenAPI
     * @param customerId
     */
    List<OpenApi> deleteByCustomer(Long customerId);

    /**
     * 根据实体ID删除
     * @param entityId
     * @return
     */
    List<OpenApi> deleteByEntity(Long entityId);
}
