package com.elecredit.op.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.constants.BatchPlanStatus;
import com.elecredit.op.constants.BatchTaskStatus;
import com.elecredit.op.dao.BatchQueryTaskDao;
import com.elecredit.op.dao.BatchQueryTaskPlanDao;
import com.elecredit.op.model.BatchQueryTask;
import com.elecredit.op.model.BatchQueryTaskPlan;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhaoyou
 * Date:2018/10/9
 * Time:10:06
 */
@Component
public class BatchQueryTaskServiceImpl implements BatchQueryTaskService {
    @Autowired
    private BatchQueryTaskDao batchQueryTaskDao;
    @Autowired
    private BatchQueryTaskPlanDao batchQueryTaskPlanDao;
    @Override
    public BatchQueryTask getById(Object... objects) {
        return batchQueryTaskDao.getById(objects);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BatchQueryTask save(BatchQueryTask batchQueryTask) {
        for(BatchQueryTaskPlan plan:batchQueryTask.getPlanList()){
            batchQueryTaskPlanDao.save(plan);
        }
        return batchQueryTaskDao.save(batchQueryTask);
    }

    @Override
    public BatchQueryTask update(BatchQueryTask batchQueryTask) {
        return batchQueryTaskDao.update(batchQueryTask);
    }

    @Override
    public boolean deleteById(Object... objects) {
        return batchQueryTaskDao.deleteById(objects);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean pause(Long taskId) {
        batchQueryTaskDao.update("update op_batch_query_task set status=? where task_id=?", BatchTaskStatus.PAUSE,taskId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean start(Long taskId) {
        batchQueryTaskDao.update("update op_batch_query_task set status=? where task_id=?",BatchTaskStatus.WAIT_PROCESS,taskId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(Long taskId) {
        batchQueryTaskDao.update("update op_batch_query_task set status=? where task_id=?",BatchTaskStatus.CANCEL,taskId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean started(Long taskId) {
        batchQueryTaskDao.update("update op_batch_query_task set status=? where task_id=?",BatchTaskStatus.BE_PROCESS,taskId);
        return true;
    }

    @Override
    public List<BatchQueryTask> paginate(JSONObject condition, int pageNum, int pageSize) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        getWhere(condition,where,whereData);
        whereData.add(pageNum * pageSize);
        whereData.add(pageSize);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return  batchQueryTaskDao.findList("select * from op_batch_query_task where 1=1" + whereStr + " order by task_id desc limit ?,?",whereData.toArray());
    }

    @Override
    public Long count(JSONObject condition) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        getWhere(condition,where,whereData);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return batchQueryTaskDao.getJdbcTemplate().queryForObject("select count(1) from op_batch_query_task where 1=1" + whereStr,Long.class,whereData.toArray());
    }

    @Override
    public BatchQueryTask nextTask() {
        return null;
    }

    private void getWhere(JSONObject condition, List<String> where, List<Object> whereData){
        if(condition.getLong("customerId") != null){
            where.add("customer_id = ?");
            whereData.add(condition.getLong("customerId"));
        }
        if(condition.getLong("entityId") != null){
            where.add("entity_id = ?");
            whereData.add(condition.getLong("entityId"));
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void queryTaskAgain(Long taskId) {
        BatchQueryTask batchQueryTask = batchQueryTaskDao.getById(taskId);
        batchQueryTask.setStatus(BatchTaskStatus.WAIT_PROCESS);
        batchQueryTask.setSuccessCount(0);
        batchQueryTask.setFailCount(0);
        batchQueryTask.setNoResultCount(0);
        batchQueryTask.setPercentage(0.00f);
        batchQueryTaskDao.update(batchQueryTask);
        batchQueryTaskPlanDao.update("update op_batch_query_task_plan set status =? where task_id=?", BatchPlanStatus.WAIT_PROCESS, taskId);
    }
}
