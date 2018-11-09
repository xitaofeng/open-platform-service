package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.constants.ApiType;
import com.elecredit.op.constants.EntityType;
import com.elecredit.op.constants.PermissionType;
import com.elecredit.op.dao.ServiceApplicationDao;
import com.elecredit.op.model.DataService;
import com.elecredit.op.model.DataServiceApi;
import com.elecredit.op.model.EntityPermission;
import com.elecredit.op.model.ServiceApplication;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceApplicationServiceImpl implements ServiceApplicationService {

    private Logger logger = LoggerFactory.getLogger(ServiceApplicationServiceImpl.class);

    @Autowired
    private DataServiceApiService dataServiceApiService;
    @Autowired
    private DataServiceService dataServiceService;
    @Autowired
    private ServiceApplicationDao serviceApplicationDao;
    @Autowired
    private EntityPermissionService entityPermissionService;

    @Override
    public ServiceApplication getById(Object... id) {
        return serviceApplicationDao.getById(id);
    }

    @Override
    public ServiceApplication save(ServiceApplication model) {
        serviceApplicationDao.save(model);
        return model;
    }

    @Override
    public ServiceApplication update(ServiceApplication model) {
        return serviceApplicationDao.update(model);
    }

    @Override
    public boolean deleteById(Object... id) {
        return serviceApplicationDao.deleteById(id);
    }

    @Override
    public List<ServiceApplication> getByUserAndService(Long userId, Long serviceId) {
        return serviceApplicationDao.findList("select * from op_service_application where user_id = ? and service_id = ?",userId,serviceId);
    }
    @Override
    public ServiceApplication getLastByUserAndService(Long userId, Long serviceId) {
        return serviceApplicationDao.findOne("select * from op_service_application where user_id = ? and service_id = ? order by created_time desc",userId,serviceId);
    }
    @Override
    public List<ServiceApplication> paginate(JSONObject condition, int pageNum, int pageSize) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        String limit = "limit ?,?";
        getWhere(condition,where,whereData);
        whereData.add(pageNum * pageSize);
        whereData.add(pageSize);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return  serviceApplicationDao.findList("select * from op_service_application where 1=1" + whereStr + " order by created_time desc " + limit,whereData.toArray());
    }

    @Override
    public long count(JSONObject condition) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        getWhere(condition,where,whereData);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return serviceApplicationDao.getJdbcTemplate().queryForObject("select count(1) from op_service_application where 1=1" + whereStr,Long.class,whereData.toArray());
    }

    @Override
    public ServiceApplication check(Long applicationId, int status, Long operatorId, String operatorName, String remark) {
        ServiceApplication  serviceApplication = serviceApplicationDao.getById(applicationId);
        if(serviceApplication == null){
            throw new RuntimeException("服务申请不存在");
        }else{
            serviceApplication.setStatus(status);
            serviceApplication.setOperateTime(LocalDateTime.now());
            serviceApplication.setOperatorId(operatorId);
            serviceApplication.setOperatorName(operatorName);
            serviceApplication.setRemark(remark);

            List<EntityPermission> permissionList = new ArrayList<>();
            DataService dataService = dataServiceService.getById(serviceApplication.getServiceId());
            EntityPermission entityPermission = new EntityPermission();
            entityPermission.setEntityId(serviceApplication.getUserId());
            entityPermission.setEntityType(EntityType.USER);
            entityPermission.setPermissionId(dataService.getServiceId());
            entityPermission.setPermissionType(PermissionType.SERVICE);
            entityPermission.setPermissionDetail("");
            permissionList.add(entityPermission);

            List<DataServiceApi> apiList = dataServiceApiService.getByServiceId(serviceApplication.getServiceId());
            for(DataServiceApi dataServiceApi : apiList){
                // 非标准服务接口不添加
                if(dataServiceApi.getApiType() != ApiType.STANDARD){
                    continue;
                }
                entityPermission = new EntityPermission();
                entityPermission.setEntityId(serviceApplication.getUserId());
                entityPermission.setEntityType(EntityType.USER);
                entityPermission.setPermissionId(dataServiceApi.getServiceApiId());
                entityPermission.setPermissionType(PermissionType.SERVICE_API);
                entityPermission.setPermissionDetail("");
                permissionList.add(entityPermission);
            }
            // 更新服务申请
            serviceApplicationDao.update(serviceApplication);
            // 更新权限
            entityPermissionService.save(permissionList,false);
        }
        return serviceApplication;
    }

    private void getWhere(JSONObject condition,List<String> where, List<Object> whereData){
        if(condition.getLong("userId") != null){
            where.add("user_id = ?");
            whereData.add(condition.getLong("userId"));
        }
        if(condition.getLong("operatorId") != null){
            where.add("operator_id = ?");
            whereData.add(condition.getLong("operatorId"));
        }
    }
}
