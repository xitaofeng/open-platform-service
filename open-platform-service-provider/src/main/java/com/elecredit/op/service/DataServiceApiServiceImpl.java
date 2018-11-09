package com.elecredit.op.service;

import com.elecredit.op.constants.MessageTopic;
import com.elecredit.op.core.MessageQueueService;
import com.elecredit.op.dao.DataServiceApiDao;
import com.elecredit.op.exception.ServiceApiIdAlreadyExistException;
import com.elecredit.op.model.DataServiceApi;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataServiceApiServiceImpl implements DataServiceApiService {
    private Logger logger = LoggerFactory.getLogger(DataServiceApiServiceImpl.class);
    @Autowired
    private DataServiceApiDao dataServiceApiDao;
    @Autowired
    private MessageQueueService messageQueueService;
    @Override
    public DataServiceApi getById(Long id) {
        return dataServiceApiDao.getById(id);
    }

    @Override
    public List<DataServiceApi> getAll() {
        return dataServiceApiDao.findList("select * from op_service_api  order by sort_number,service_api_id");
    }

    @Override
    public List<DataServiceApi> getByServiceId(Long serviceId) {
        return dataServiceApiDao.findList("select * from op_service_api where service_id = ? order by sort_number,service_api_id",serviceId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataServiceApi save(DataServiceApi dataService) throws ServiceApiIdAlreadyExistException{
        try {
            dataServiceApiDao.save(dataService);
            messageQueueService.sendTopic(MessageTopic.SERVICE_API_ADD,dataService);
            return dataService;
        }catch (DuplicateKeyException e){
            if(e.getMessage().contains("key 'service_api_id'")) {
                throw new ServiceApiIdAlreadyExistException();
            }else{
                throw e;
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataServiceApi update(DataServiceApi dataServiceApi) {
        dataServiceApiDao.update(dataServiceApi);
        messageQueueService.sendTopic(MessageTopic.SERVICE_API_UPDATE, dataServiceApi);
        return dataServiceApi;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(DataServiceApi dataService) {
        return deleteById(dataService.getServiceApiId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        DataServiceApi dataServiceApi = dataServiceApiDao.cascadeDelete(id);
        messageQueueService.sendTopic(MessageTopic.SERVICE_API_DELETE,dataServiceApi);
        return true;
    }


    @Override
    public List<DataServiceApi> getByUser(Long userId, String key) {
        ArrayList<String> whereClause = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();

        if(StringUtil.isNotEmpty(key)){
            whereClause.add("service_api_name like ?");
            whereData.add("%"+key+"%");
        }

        String where = whereClause.size() > 0 ? " and " + StringUtil.join(whereClause.toArray()," and ") :"";
        return dataServiceApiDao.findList("select a.* from op_service_api a,permission_settings b where  a.service_api_id=b.permission_id and b.entity_id = " + userId  + where + " order by a.sort_number,a.service_api_id",whereData.toArray());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrder(List<DataServiceApi> dataServiceApiList) {
        for(DataServiceApi dataServiceApi : dataServiceApiList){
            dataServiceApiDao.update("update op_service_api set sort_number = ? where service_api_id = ? ",dataServiceApi.getSortNumber(),dataServiceApi.getServiceApiId());
        }
        return true;
    }


}
