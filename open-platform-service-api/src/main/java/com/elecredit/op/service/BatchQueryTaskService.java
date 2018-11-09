package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.model.BatchQueryTask;
import com.elecredit.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhaoyou
 * Date:2018/10/9
 * Time:9:52
 */
public interface BatchQueryTaskService extends BaseService<BatchQueryTask>{
    /**
     * 暂停任务
     * @param taskId
     */
    boolean pause(Long taskId);
    /**
     * 启动任务
     * @param taskId
     */
    boolean start(Long taskId);
    /**
     * 取消任务
     * @param taskId
     */
    boolean cancel(Long taskId);

    @Transactional(rollbackFor = Exception.class)
    boolean started(Long taskId);

    /**
     * 获取第N页的批量查询任务列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<BatchQueryTask> paginate(JSONObject condition, int pageNum, int pageSize);
    /**
     * 返回记录总数
     * @return
     */
    Long count(JSONObject condition);

    /**
     * 获取下一个待处理的任务
     * @return
     */
    BatchQueryTask nextTask();

    /**
     * 根据任务ID重新查询任务
     * @param taskId
     */
    void queryTaskAgain(Long taskId);
}
