package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.dao.PaymentRecordDao;
import com.elecredit.op.model.PaymentRecord;
import com.elecredit.service.IDGenerateService;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentRecordServiceImpl implements PaymentRecordService {

    private Logger logger = LoggerFactory.getLogger(PaymentRecordServiceImpl.class);

    @Autowired
    private PaymentRecordDao invoiceRecordDao;
    @Autowired
    private IDGenerateService idGenerateService;

    @Override
    public List<PaymentRecord> paginate(JSONObject condition, int pageNum, int pageSize) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        getWhere(condition,where,whereData);
        whereData.add(pageNum * pageSize);
        whereData.add(pageSize);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return  invoiceRecordDao.findList("select * from off_payment_record where 1=1" + whereStr + " order by created_time desc limit ?,?",whereData.toArray());
    }

    @Override
    public Long paginateCount(JSONObject condition) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        getWhere(condition,where,whereData);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return invoiceRecordDao.getJdbcTemplate().queryForObject("select count(1) from off_payment_record where 1=1" + whereStr,Long.class,whereData.toArray());
    }

    @Override
    public PaymentRecord getById(Long id) {
        return invoiceRecordDao.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentRecord save(PaymentRecord invoiceRecord) {
        invoiceRecord.setPaymentId(idGenerateService.getId());
        invoiceRecord.setCreatedTime(LocalDateTime.now());
        return invoiceRecordDao.save(invoiceRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentRecord update(PaymentRecord model) {
        return invoiceRecordDao.update(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Object id) {
        return invoiceRecordDao.deleteById(id);
    }

    private void getWhere(JSONObject condition,List<String> where, List<Object> whereData){
        if(condition.getLong("customerId") != null){
            where.add("customer_id = ?");
            whereData.add(condition.getLong("customerId"));
        }
    }
}
