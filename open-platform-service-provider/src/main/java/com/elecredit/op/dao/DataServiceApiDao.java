package com.elecredit.op.dao;

import com.elecredit.common.dao.BaseDao;
import com.elecredit.op.model.DataServiceApi;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataServiceApiDao extends BaseDao<DataServiceApi> {

    @Transactional(rollbackFor = Exception.class)
    public DataServiceApi cascadeDelete(Long serviceApiId){
        DataServiceApi dataServiceApi = getById(serviceApiId);
        update("delete from op_permission_settings where permission_id = ?",serviceApiId);
        deleteById(serviceApiId);
        return dataServiceApi;
    }
}
