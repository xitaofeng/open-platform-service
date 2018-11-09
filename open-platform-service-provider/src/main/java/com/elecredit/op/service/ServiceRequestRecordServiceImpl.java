package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.model.ServiceApplication;
import com.elecredit.op.model.ServiceRequestRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceRequestRecordServiceImpl implements ServiceRequestRecordService {

    private static String COLLECTION_NAME = "service_request_record";

    private Logger logger = LoggerFactory.getLogger(ServiceRequestRecordServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public ServiceRequestRecord getByRequestSn(String requestSn) {
        return mongoTemplate.findOne(Query.query(Criteria.where("requestSn").is(requestSn)),ServiceRequestRecord.class,COLLECTION_NAME);
    }

    @Override
    public ServiceRequestRecord getByClientRequestSn(String clientRequestSn) {
        return mongoTemplate.findOne(Query.query(Criteria.where("clientRequestSn").is(clientRequestSn)),ServiceRequestRecord.class,COLLECTION_NAME);
    }

    @Override
    public List<ServiceRequestRecord> paginate(JSONObject condition, int pageNum, int pageSize) {
        Query query = getQuery(condition);
        if(condition.get("withResult") == null || condition.getInteger("withResult") == 0) {
            query.fields().exclude("result");
        }
        query.limit(pageSize).skip(pageNum * pageSize);
        query.with(Sort.by(Sort.Direction.DESC,"requestTime"));
        List<ServiceRequestRecord> serviceRequestRecordList = mongoTemplate.find(query,ServiceRequestRecord.class,COLLECTION_NAME);
        return serviceRequestRecordList;
    }

    @Override
    public long count(JSONObject condition) {
        return mongoTemplate.count(getQuery(condition),COLLECTION_NAME);
    }


    @Override
    public ServiceApplication getById(Object... objects) {
        return null;
    }

    @Override
    public ServiceApplication save(ServiceApplication serviceApplication) {
        return null;
    }

    @Override
    public ServiceApplication update(ServiceApplication serviceApplication) {
        return null;
    }

    @Override
    public boolean deleteById(Object... objects) {
        return false;
    }


    private Query getQuery(JSONObject condition){
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);
        if (condition.containsKey("customerId")){
            criteria.and("customerId").is(condition.getLong("customerId"));
        }
        if (condition.containsKey("entityId")){
            criteria.and("entityId").is(condition.getLong("entityId"));
        }
        if (condition.containsKey("entityType")){
            criteria.and("entityType").is(condition.getLong("entityType"));
        }
        if (condition.containsKey("serviceId")){
            criteria.and("serviceId").is(condition.getLong("serviceId"));
        }
        if (condition.containsKey("apiId")){
            criteria.and("apiId").is(condition.getLong("apiId"));
        }
        if (condition.containsKey("taskId")){
            criteria.and("taskId").is(condition.getLong("taskId"));
        }
        return  query;
    }
}
