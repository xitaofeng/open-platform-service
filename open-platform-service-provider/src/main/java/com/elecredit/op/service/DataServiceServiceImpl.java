package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.constants.EntityType;
import com.elecredit.op.dao.DataServiceApiDao;
import com.elecredit.op.dao.DataServiceDao;
import com.elecredit.op.dao.SupplierDao;
import com.elecredit.op.dao.SupplierServiceDao;
import com.elecredit.op.exception.ServiceIdAlreadyExistException;
import com.elecredit.op.model.*;
import com.elecredit.user.model.User;
import com.elecredit.user.service.UserService;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class DataServiceServiceImpl implements DataServiceService {
    private Logger logger = LoggerFactory.getLogger(DataServiceServiceImpl.class);
    @Autowired
    private DataServiceDao dataServiceDao;
    @Autowired
    private DataServiceApiDao dataServiceApiDao;
    @Autowired
    private DataServiceApiService dataServiceApiService;
    @Autowired
    private SupplierServiceDao supplierServiceDao;
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private EntityPermissionService entityPermissionService;
    @Override
    public DataService getById(Long id) {
        return getById(id,false,false);
    }

    @Override
    public DataService getById(Long id, boolean withApi, boolean withSupplier) {

        DataService dataService = dataServiceDao.getById(id);
        if(dataService != null) {
            if (withSupplier) {
                dataService.setSupplierList(supplierServiceDao.getByServiceId(id));
            }

            if (withApi) {
                dataService.setApiList(dataServiceApiDao.findList("select * from op_service_api where service_id = ?", dataService.getServiceId()));
            }
        }
        return dataService;
    }

    @Override
    public List<DataService> getAll() {
        return getAll(false,false);
    }

    @Override
    public List<DataService> getAll(boolean withApi, boolean withSupplier) {

        List<DataService> dataServiceList = dataServiceDao.findList("select * from op_service order by sort_number,service_id");
        if(dataServiceList != null) {
            for(DataService dataService : dataServiceList){
                if (withSupplier) {
                    dataService.setSupplierList(supplierServiceDao.getByServiceId(dataService.getServiceId()));
                }
                if (withApi) {
                    dataService.setApiList(dataServiceApiDao.findList("select * from op_service_api where service_id = ? order by sort_number,service_api_id", dataService.getServiceId()));
                }
            }
        }
        return dataServiceList;
    }

    @Override
    public List<DataService> getByParent(Long parentId) {
        return dataServiceDao.findList("select * from op_service where parent = ? order by sort_number,service_id",parentId);
    }

    @Override
    public List<DataService> getAllByParent(Long parentId) {

        return getAllByParent(parentId,false);
    }

    @Override
    public List<DataService> getAllByParent(Long parentId, boolean containSelf) {

        List<DataService> result = new ArrayList<DataService>();
        if(containSelf)
            result.add(getById(parentId));

        List<DataService> services = getByParent(parentId);

        if(!services.isEmpty()) {

            for (DataService service : services) {
                result.add(service);
                result.addAll(getChildrenByParent(service));
            }
        }
        return result;
    }

    public List<DataService> getChildrenByParent(DataService parent){

        List<DataService> result = new ArrayList<DataService>();
        List<DataService> services = getByParent(parent.getServiceId());
        if(!services.isEmpty()) {

            for (DataService service : services) {
                result.add(service);
                result.addAll(getChildrenByParent(service));
            }
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataService save(DataService dataService) throws ServiceIdAlreadyExistException{
        if(dataService.getSupplierList() != null)
            for(Supplier supplier : dataService.getSupplierList()){
                SupplierServiceConfig supplierServiceConfig = new SupplierServiceConfig();
                supplierServiceConfig.setServiceId(dataService.getServiceId());
                supplierServiceConfig.setSupplierId(supplier.getSupplierId());
                supplierServiceDao.save(supplierServiceConfig);
            }
        return dataServiceDao.save(dataService);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataService update(DataService dataService) {

        supplierServiceDao.update("delete from op_supplier_service where service_id = ? ",dataService.getServiceId());
        if(dataService.getSupplierList() != null) {
            for (Supplier supplier : dataService.getSupplierList()) {
                SupplierServiceConfig supplierServiceConfig = new SupplierServiceConfig();
                supplierServiceConfig.setServiceId(dataService.getServiceId());
                supplierServiceConfig.setSupplierId(supplier.getSupplierId());
                supplierServiceDao.save(supplierServiceConfig);
            }
        }
        dataService = dataServiceDao.update(dataService);
        // 更新服务名称
        List<DataServiceApi> dataServiceApiList = dataServiceApiService.getByServiceId(dataService.getServiceId());
        for(DataServiceApi dataServiceApi : dataServiceApiList){
            dataServiceApi.setServiceName(dataService.getServiceName());
            dataServiceApiService.update(dataServiceApi);
        }
        return dataService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(DataService dataService) {

        if(dataService != null) {
            return deleteById(dataService.getServiceId());
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {

        supplierServiceDao.update("delete from op_supplier_service where service_id = ?",id);
        return dataServiceDao.cascadeDelete(id);
    }

    @Override
    public List<String> categoryList() {
        Set<String> categorySet = new HashSet<>();
        List<DataService> serviceList = dataServiceDao.findList("select * from op_service ");

        if(serviceList != null)
            for(DataService service : serviceList){
                String[] categories = service.getCategory().split(",");
                if(categories.length != 0){
                    for(String category : categories)
                        categorySet.add(category);
                }else
                    categorySet.add(service.getCategory());
            }
        List<String> categoryList = new ArrayList<>(categorySet);
        return categoryList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean categoryMerge(List<String> categoryList, String name) {

        for(String category : categoryList ){
            dataServiceDao.update("update op_service set category = replace(category,'"+ category +"','"+  name +"')");
        }

        Set<String> set = new HashSet<>();
        List<DataService> serviceList = dataServiceDao.findList("select * from op_service where category like '%,%'");

        if(serviceList != null)
            for(DataService service : serviceList){
                set.clear();
                for(String category : service.getCategory().split(",")){
                    set.add(category);
                }
                service.setCategory(StringUtil.join(set.toArray(),","));
                dataServiceDao.update(service);
            }
        return true;
    }

    @Override
    public List<DataService> getByUser(JSONObject condition) {
        Long userId = condition.getLong("userId");
        String category = condition.getString("category");
        String key = condition.getString("key");
        Integer status = condition.getInteger("status");
        User user = userService.getById(userId);
        if(user == null){
            return Collections.EMPTY_LIST;
        }

        ArrayList<String> whereClause = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        if(StringUtil.isNotEmpty(category)){
            whereClause.add("category like ?");
            whereData.add("%"+category+"%");
        }
        if(StringUtil.isNotEmpty(key)){
            whereClause.add("service_name like ?");
            whereData.add("%"+key+"%");
        }
        if(StringUtil.isNotEmpty(key)){
            whereClause.add("service_name like ?");
            whereData.add("%"+key+"%");
        }
        if(status != null){
            whereClause.add("status = ?");
            whereData.add(status);
        }
        List<EntityPermission> userPermissions = entityPermissionService.getAllByEntity(userId, EntityType.USER);
        if(!userPermissions.isEmpty()) {
            String[] placeholders = new String[userPermissions.size()];
            Arrays.fill(placeholders, "?");
            for (EntityPermission entityPermission : userPermissions) {
                whereData.add(entityPermission.getPermissionId());
            }
            whereClause.add("a.service_id in(" + StringUtil.join(placeholders, " , ") + ")");
        }else{
            whereClause.add("false");
        }
        String where = whereClause.size() > 0 ? " and " + StringUtil.join(whereClause.toArray()," and ") :"";
        return dataServiceDao.findList("select a.* from op_service a where 1=1" + where + " order by a.sort_number,a.service_id",whereData.toArray());
    }

    @Override
    public List<DataService> getByPage(JSONObject condition, int pageNum, int pageSize) {
        //int pageNum, int pageSize, String category, String key, boolean withApi, boolean withSupplier
        int offset = pageNum * pageSize;

        ArrayList<String> whereClause = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();

        getWhere(condition,whereClause,whereData);

        String where = whereClause.size() > 0 ? " where " + StringUtil.join(whereClause.toArray()," and ") :"";

        List<DataService> dataServiceList = dataServiceDao.findList("select * from op_service " + where + " order by sort_number,service_id limit " + offset + "," + pageSize,whereData.toArray());

        Integer withSupplier = condition.getInteger("withSupplier");
        Integer withApi = condition.getInteger("withApi");

        if(dataServiceList != null) {
            for(DataService dataService : dataServiceList){
                if (withSupplier != null && withSupplier == 1) {
                    dataService.setSupplierList(supplierServiceDao.getByServiceId(dataService.getServiceId()));
                }
                if (withApi != null && withApi == 1) {
                    dataService.setApiList(dataServiceApiDao.findList("select * from op_service_api where service_id = ? order by sort_number,service_api_id", dataService.getServiceId()));
                }
            }
        }

        return dataServiceList;
    }


    @Override
    public Long count(JSONObject condition) {

        ArrayList<String> whereClause = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();

        getWhere(condition,whereClause,whereData);

        String where = whereClause.size() > 0 ? " where " + StringUtil.join(whereClause.toArray()," and ") :"";

        return dataServiceDao.count(where,whereData.toArray());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrder(List<DataService> dataServiceList) {
        for(DataService dataService : dataServiceList){
            dataServiceDao.update("update op_service set sort_number = ? where service_id = ? ",dataService.getSortNumber(),dataService.getServiceId());
        }
        return true;
    }

    private void getWhere(JSONObject condition, List<String> where, List<Object> whereData){
        String category = condition.getString("category");
        String key = condition.getString("key");
        Integer status = condition.getInteger("status");

        if(StringUtil.isNotEmpty(category)){
            where.add("category like ?");
            whereData.add("%" + category + "%");
        }
        if(StringUtil.isNotEmpty(key)){
            where.add("service_name like ?");
            whereData.add("%" + key+ "%");
        }
        if(status != null){
            where.add("status = ?");
            whereData.add(status);
        }
    }
}
