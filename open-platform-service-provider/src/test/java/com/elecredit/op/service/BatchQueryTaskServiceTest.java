package com.elecredit.op.service;

import com.elecredit.SuperTest;
import com.elecredit.op.constants.BatchTaskStatus;
import com.elecredit.op.dao.BatchQueryTaskDao;
import com.elecredit.op.model.BatchQueryTask;
import com.elecredit.op.model.BatchQueryTaskPlan;
import com.elecredit.service.IDGenerateService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BatchQueryTaskServiceTest extends SuperTest {
    @Autowired
    BatchQueryTaskService batchQueryTaskService;
    @Autowired
    IDGenerateService idGenerateService;
    @Test
    public void save(){
        long timeSpend = 0;
            long id = idGenerateService.getId();

        BatchQueryTask batchQueryTask=new BatchQueryTask();
        batchQueryTask.setTaskId(id);
        batchQueryTask.setServiceName("sdfa");
        batchQueryTask.setServiceId(11l);
        batchQueryTask.setEntityName("sfads");
        batchQueryTask.setEntityType(2);
        batchQueryTask.setEntityId(111L);
        batchQueryTask.setCustomerName("sdfas");
        batchQueryTask.setCustomerId(2123L);
        batchQueryTask.setAccountName("sdfasdfs");
        batchQueryTask.setAccountId(12L);
        batchQueryTask.setCreatedTime(LocalDateTime.now());
        batchQueryTask.setStatus(BatchTaskStatus.WAIT_PROCESS);

        List<BatchQueryTaskPlan> batchQueryTaskPlanList=new ArrayList<>();

            BatchQueryTaskPlan batchQueryTaskPlan=new BatchQueryTaskPlan();
            batchQueryTaskPlan.setPlanId(idGenerateService.getId());
            batchQueryTaskPlan.setParams("1111");
            batchQueryTaskPlan.setServiceId(11l);
            batchQueryTaskPlan.setServiceName("sdfas");
            batchQueryTaskPlan.setStatus(1);
            batchQueryTaskPlan.setTaskId(id);
            batchQueryTaskPlanList.add(batchQueryTaskPlan);
            batchQueryTask.setPlanList(batchQueryTaskPlanList);
            long time = System.currentTimeMillis();
        batchQueryTaskService.save(batchQueryTask);
            timeSpend += (System.currentTimeMillis() - time);
        System.out.println("插入用时：" + timeSpend);
    }
}