package com.elecredit.op.dao;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.SuperTest;
import com.elecredit.op.model.BatchQueryTask;
import com.elecredit.op.model.BatchQueryTaskPlan;
import com.elecredit.op.model.Menu;
import com.elecredit.service.IDGenerateService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BatchQueryTaskDaoTest extends SuperTest {
    @Autowired
    BatchQueryTaskDao batchQueryTaskDao;
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
        batchQueryTask.setStatus(1);
        batchQueryTask.setCompleteTime(LocalDateTime.now());
            long time = System.currentTimeMillis();
            batchQueryTaskDao.save(batchQueryTask);
            timeSpend += (System.currentTimeMillis() - time);
        System.out.println("插入用时：" + timeSpend);
    }
}