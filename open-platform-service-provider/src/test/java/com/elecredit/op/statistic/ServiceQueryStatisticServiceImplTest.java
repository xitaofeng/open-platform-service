package com.elecredit.op.statistic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.elecredit.SuperTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceQueryStatisticServiceImplTest extends SuperTest {
    @Autowired
    private ServiceQueryStatisticServiceImpl customerQueryStatisticService;
    @Test
    public void statistic() {
        JSONObject conditions = new JSONObject();
        System.out.println(JSON.toJSONString(customerQueryStatisticService.statisticByCustomer(conditions)));
    }
}