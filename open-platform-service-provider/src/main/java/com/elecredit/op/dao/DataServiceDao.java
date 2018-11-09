package com.elecredit.op.dao;

import com.elecredit.common.dao.BaseDao;
import com.elecredit.op.model.DataService;
import com.elecredit.op.model.DataServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DataServiceDao extends BaseDao<DataService> {

    @Autowired
    private DataServiceApiDao dataServiceApiDao;

    @Transactional(rollbackFor = Exception.class)
    public boolean cascadeDelete(Long serviceId){

        List<DataService> dataServiceList = findList("select * from op_service where parent = ?",serviceId);
        if(dataServiceList != null)
            for(DataService dataService : dataServiceList){

                update("delete from op_permission_settings where permission_id = ?", dataService.getServiceId());
                cascadeDelete(dataService.getServiceId());
            }

        List<DataServiceApi> dataServiceApiList = dataServiceApiDao.findList("select * from op_service_api where service_id = '"+serviceId+"'");
        if(dataServiceApiList != null)
            for(DataServiceApi dataServiceApi : dataServiceApiList){

                dataServiceApiDao.cascadeDelete(dataServiceApi.getServiceApiId());
            }

        update("delete from op_permission_settings where permission_id = ?",serviceId);
        deleteById(serviceId);
        return true;
    }
}
