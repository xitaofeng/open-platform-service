package com.elecredit.op.statistic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.constants.ConditionPeriodType;
import com.elecredit.user.model.Customer;
import com.elecredit.user.service.CustomerService;
import jodd.util.StringUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ServiceQueryStatisticServiceImpl implements ServiceQueryStatisticService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CustomerService customerService;

    private static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public JSONArray statisticByCustomer(JSONObject conditions) throws DateTimeParseException {
        Criteria criteria = getCriteria(conditions);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("customerId").count().as("total").first("customerName").as("customerName"),
                Aggregation.project("customerId","customerName","total"));

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation,"service_request_record", Document.class);

        JSONArray result = new JSONArray();
        for (Iterator<Document> iterator = results.iterator(); iterator.hasNext(); ) {
            Document obj = iterator.next();
            result.add(JSONObject.toJSON(obj));
        }
        return result;
    }

    @Override
    public JSONArray statisticByService(JSONObject conditions) throws DateTimeParseException {
        Criteria criteria = getCriteria(conditions);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("serviceId").count().as("total").first("serviceName").as("serviceName"),
                Aggregation.project("serviceId","serviceName","total"));

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation,"service_request_record", Document.class);

        JSONArray result = new JSONArray();
        for (Iterator<Document> iterator = results.iterator(); iterator.hasNext(); ) {
            Document obj = iterator.next();
            result.add(JSONObject.toJSON(obj));
        }
        return result;
    }

    private Criteria getCriteria(JSONObject conditions){
        Integer period = conditions.getInteger("period");
        String dateStart = conditions.getString("dateStart");
        String dateEnd = conditions.getString("dateEnd");
        Long marketPersonId = conditions.getLong("marketPersonId");
        Criteria criteria = new Criteria();
        if(period == null && StringUtil.isEmpty(dateStart) && StringUtil.isEmpty(dateEnd)){
            period = ConditionPeriodType.MONTH;
        }
        if(period != null) {
            criteria = Criteria.where("requestTime");
            if(ConditionPeriodType.TODAY == period) {
                LocalDateTime begin = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
                criteria.gte(begin).lt(begin.plusDays(1));
            }else if(ConditionPeriodType.YESTERDAY == period) {
                LocalDateTime begin = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
                criteria.gte(begin.minusDays(1)).lt(begin);
            }else if(ConditionPeriodType.MONTH == period) {
                LocalDate now = LocalDate.now();
                LocalDate begin = LocalDate.of(now.getYear(),now.getMonth(),1);
                criteria.gte(begin).lt(begin.plusMonths(1));
            }else if(ConditionPeriodType.YEAR == period) {
                LocalDate now = LocalDate.now();
                LocalDate begin = LocalDate.of(now.getYear(),1,1);
                criteria.gte(begin).lt(begin.plusYears(1));
            }else{
                LocalDate now = LocalDate.now();
                LocalDate begin = LocalDate.of(now.getYear(),now.getMonth(),1);
                criteria.gte(begin).lt(begin.plusMonths(1));
            }
        }else {
            if(StringUtil.isNotEmpty(dateStart) || StringUtil.isNotEmpty(dateEnd)){
                criteria = Criteria.where("requestTime");
            }
            if(StringUtil.isNotEmpty(dateStart)){
                LocalDateTime begin = LocalDateTime.parse(dateStart, DATETIME_FORMATTER);
                criteria.gte(begin);
            }
            if(StringUtil.isNotEmpty(dateEnd)){
                LocalDateTime end = LocalDateTime.parse(dateEnd, DATETIME_FORMATTER).plusNanos(999999999);
                criteria.lte(end);
            }
        }
        if(marketPersonId != null){
            List<Customer> customerList = customerService.getByMarketPerson(marketPersonId);
            List<Long> customerIds = new ArrayList<>();
            for (int i = 0; i < customerList.size(); i++) {
                customerIds.add(customerList.get(i).getCustomerId());
            }
            criteria.and("customerId").in(customerIds);
        }
        return criteria;
    }
}