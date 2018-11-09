package com.elecredit.op.statistic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeParseException;

/**
 * 服务查询统计
 */
@Component
public interface ServiceQueryStatisticService {

    JSONArray statisticByCustomer(JSONObject conditions) throws DateTimeParseException;

    JSONArray statisticByService(JSONObject conditions) throws DateTimeParseException;
}
