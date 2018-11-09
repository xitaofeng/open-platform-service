package com.elecredit.op.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.model.BatchQueryTaskPlan;
import com.elecredit.service.BaseService;

import java.util.List;

/**
 * Created by zhaoyou
 * Date:2018/10/9
 * Time:9:52
 */
public interface BatchQueryTaskPlanService extends BaseService<BatchQueryTaskPlan>{
    /**
     * 获取待处理的任务
     * @param size
     * @return
     */
    List<BatchQueryTaskPlan> getPendingHandlePlan(int size);

    /**
     * 处理完成
     * @param planId
     * @param taskId
     * @param status
     * @param resultId
     */
    void complete(long planId,long taskId,int status,String resultId);
    /**
     * 分页查询
     * @param condition
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<BatchQueryTaskPlan> paginate(JSONObject condition, int pageNum, int pageSize);
    /**
     * 返回记录总数
     * @return
     */
    Long count(JSONObject condition);

    /**
     * 重新查询部分计划
     * @param taskId
     * @param plans
     */
    void queryPlanAgain(Long taskId, JSONArray plans);

}
