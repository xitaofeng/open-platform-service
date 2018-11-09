package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.dao.InvoiceRecordDao;
import com.elecredit.op.model.InvoiceRecord;
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
public class InvoiceRecordServiceImpl implements InvoiceRecordService {

    private Logger logger = LoggerFactory.getLogger(InvoiceRecordServiceImpl.class);

    @Autowired
    private InvoiceRecordDao invoiceRecordDao;
    @Autowired
    private IDGenerateService idGenerateService;

    @Override
    public List<InvoiceRecord> paginate(JSONObject condition, int pageNum, int pageSize) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        getWhere(condition,where,whereData);
        whereData.add(pageNum * pageSize);
        whereData.add(pageSize);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return  invoiceRecordDao.findList("select * from off_invoice_record where 1=1" + whereStr + " order by created_time desc limit ?,?",whereData.toArray());
    }

    @Override
    public Long paginateCount(JSONObject condition) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        getWhere(condition,where,whereData);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return invoiceRecordDao.getJdbcTemplate().queryForObject("select count(1) from off_invoice_record where 1=1" + whereStr,Long.class,whereData.toArray());
    }

    @Override
    public InvoiceRecord getById(Long id) {
        return invoiceRecordDao.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceRecord save(InvoiceRecord invoiceRecord) {
        invoiceRecord.setInvoiceId(idGenerateService.getId());
        invoiceRecord.setCreatedTime(LocalDateTime.now());
        return invoiceRecordDao.save(invoiceRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceRecord update(InvoiceRecord model) {
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
