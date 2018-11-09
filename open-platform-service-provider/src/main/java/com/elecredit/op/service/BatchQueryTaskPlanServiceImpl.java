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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.elecredit.op.constants.BatchPlanStatus.SUCCESS;

/**
 * Created by zhaoyou
 * Date:2018/10/9
 * Time:10:06
 */
@Component
public class BatchQueryTaskPlanServiceImpl implements BatchQueryTaskPlanService {
    @Autowired
    private BatchQueryTaskPlanDao batchQueryTaskPlanDao;
    @Autowired
    private BatchQueryTaskDao batchQueryTaskDao;

    @Override
    public BatchQueryTaskPlan getById(Object... objects) {
        return batchQueryTaskPlanDao.getById(objects);
    }

    @Override
    public BatchQueryTaskPlan save(BatchQueryTaskPlan batchQueryTaskPlan) {
        return batchQueryTaskPlanDao.save(batchQueryTaskPlan);
    }

    @Override
    public BatchQueryTaskPlan update(BatchQueryTaskPlan batchQueryTaskPlan) {
        return batchQueryTaskPlanDao.update(batchQueryTaskPlan);
    }

    @Override
    public boolean deleteById(Object... objects) {
        return batchQueryTaskPlanDao.deleteById(objects);
    }

    @Override
    public List<BatchQueryTaskPlan> getPendingHandlePlan(int size) {
        return batchQueryTaskPlanDao.findList("select b.* from op_batch_query_task a,op_batch_query_task_plan b where a.task_id = b.task_id and a.`status` in (1,2) and b.`status` = 1 order by a.created_time limit ?",size);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void complete(long planId, long taskId, int status, String resultId) {
        batchQueryTaskPlanDao.update("update op_batch_query_task_plan set status = ?,result_id = ? where plan_id = ?", status, resultId, planId);
        String updateFiled;
        if (BatchPlanStatus.FAIL == status) {
            updateFiled = "fail_count";
        } else if (BatchPlanStatus.NO_RESULT == status) {
            updateFiled = "no_result_count";
        } else {
            updateFiled = "success_count";
        }
        batchQueryTaskDao.update("update op_batch_query_task set " + updateFiled + " = " + updateFiled + " + 1,percentage = (success_count + no_result_count +fail_count) / total_records * 100 where task_id = ?", taskId);

        BatchQueryTask batchQueryTask = batchQueryTaskDao.getById(taskId);
        if(batchQueryTask.getPercentage() >= 100){
            batchQueryTask.setCompleteTime(LocalDateTime.now());
            batchQueryTask.setStatus(BatchTaskStatus.COMPLETE);
            batchQueryTaskDao.update(batchQueryTask);
        }
    }

    @Override
    public List<BatchQueryTaskPlan> paginate(JSONObject condition, int pageNum, int pageSize) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        getWhere(condition,where,whereData);
        whereData.add(pageNum * pageSize);
        whereData.add(pageSize);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return  batchQueryTaskPlanDao.findList("select * from op_batch_query_task_plan where 1=1" + whereStr + " order by task_id desc limit ?,?",whereData.toArray());
    }

    @Override
    public Long count(JSONObject condition) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        getWhere(condition,where,whereData);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return batchQueryTaskPlanDao.getJdbcTemplate().queryForObject("select count(1) from op_batch_query_task_plan where 1=1" + whereStr,Long.class,whereData.toArray());
    }

    private void getWhere(JSONObject condition, List<String> where, List<Object> whereData){
        if(condition.getLong("taskId") != null){
            where.add("task_id = ?");
            whereData.add(condition.getLong("taskId"));
        }
        if (condition.getInteger("status") != null) {
            where.add("status=?");
            whereData.add(condition.getInteger("status"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void queryPlanAgain(Long taskId, JSONArray plans) {
        BatchQueryTask batchQueryTask = batchQueryTaskDao.getById(taskId);
        Integer successCount=batchQueryTask.getSuccessCount();
        Integer failCount=batchQueryTask.getFailCount();
        Integer noResultCount=batchQueryTask.getNoResultCount();
        Integer totalRecords=batchQueryTask.getTotalRecords();
        batchQueryTask.setStatus(BatchTaskStatus.WAIT_PROCESS);

        for (Object plan : plans) {
            JSONObject jsonObject = (JSONObject) plan;
            Long planId = jsonObject.getLong("planId");
            BatchQueryTaskPlan batchQueryTaskPlan = batchQueryTaskPlanDao.getById(planId);
            int status = batchQueryTaskPlan.getStatus();
            if(BatchPlanStatus.SUCCESS==status){
                successCount--;
            }else if(BatchPlanStatus.NO_RESULT==status){
                noResultCount--;
            }else if(BatchPlanStatus.FAIL==status){
                failCount--;
            }
            batchQueryTaskPlanDao.update("update op_batch_query_task_plan set status =? where plan_id=?", BatchPlanStatus.WAIT_PROCESS, planId);
        }
        batchQueryTask.setSuccessCount(successCount);
        batchQueryTask.setNoResultCount(noResultCount);
        batchQueryTask.setFailCount(failCount);
        DecimalFormat df=new DecimalFormat("0.00");
        float queryCount=(float) (successCount+noResultCount+failCount);
        batchQueryTask.setPercentage(Float.valueOf(df.format(queryCount/(float)totalRecords)));
        batchQueryTaskDao.update(batchQueryTask);
    }

}
