package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.model.PaymentRecord;

import java.util.List;

/**
 * 回款记录服务
 * @author yangfei
 */
public interface PaymentRecordService {
    /**
     * 保存回款记录信息
     * @param PaymentRecord
     * @return
     */
    PaymentRecord save(PaymentRecord PaymentRecord);

    /**
     * 获取回款记录信息
     * @param PaymentRecordId
     * @return
     */
    PaymentRecord getById(Long PaymentRecordId);

    /**
     * 回款记录列表，分页查询
     * @param condition 查询条件
     * @param pageNum 页码，从0开始
     * @param pageSize 每页条数
     * @return
     */
    List<PaymentRecord> paginate(JSONObject condition, int pageNum, int pageSize);

    /**
     * 获取回款记录列表总数
     * @return
     */
    Long paginateCount(JSONObject condition);

    /**
     * 更新回款记录信息
     * @param model
     * @return
     */
    PaymentRecord update(PaymentRecord model);

    /**
     * 删除回款记录信息
     * @param id
     * @return
     */
    public boolean deleteById(Object id);
}
