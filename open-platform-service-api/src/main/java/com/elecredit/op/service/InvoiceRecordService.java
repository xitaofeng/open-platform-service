package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.model.InvoiceRecord;

import java.util.List;

/**
 * 开票记录服务
 * @author yangfei
 */
public interface InvoiceRecordService {
    /**
     * 保存开票记录信息
     * @param InvoiceRecord
     * @return
     */
    InvoiceRecord save(InvoiceRecord InvoiceRecord);

    /**
     * 获取开票记录信息
     * @param InvoiceRecordId
     * @return
     */
    InvoiceRecord getById(Long InvoiceRecordId);

    /**
     * 开票记录列表，分页查询
     * @param condition 查询条件
     * @param pageNum 页码，从0开始
     * @param pageSize 每页条数
     * @return
     */
    List<InvoiceRecord> paginate(JSONObject condition, int pageNum, int pageSize);

    /**
     * 获取开票记录列表总数
     * @return
     */
    Long paginateCount(JSONObject condition);

    /**
     * 更新开票记录信息
     * @param model
     * @return
     */
    InvoiceRecord update(InvoiceRecord model);

    /**
     * 删除开票记录信息
     * @param id
     * @return
     */
    public boolean deleteById(Object id);
}
