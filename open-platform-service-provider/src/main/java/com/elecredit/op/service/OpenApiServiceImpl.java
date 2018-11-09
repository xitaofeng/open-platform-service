package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.constants.EntityType;
import com.elecredit.op.dao.OpenApiDao;
import com.elecredit.op.model.OpenApi;
import com.elecredit.service.IDGenerateService;
import com.elecredit.user.constants.AccountLimitType;
import com.elecredit.user.constants.AccountType;
import com.elecredit.user.model.Account;
import com.elecredit.user.model.Customer;
import com.elecredit.user.model.Group;
import com.elecredit.user.model.User;
import com.elecredit.user.service.AccountService;
import com.elecredit.user.service.CustomerService;
import com.elecredit.user.service.GroupService;
import com.elecredit.user.service.UserService;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OpenApiServiceImpl implements OpenApiService {

    private Logger logger = LoggerFactory.getLogger(OpenApiServiceImpl.class);

    @Autowired
    private OpenApiDao openApiDao;
    @Autowired
    private IDGenerateService idGenerateService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private CustomerService customerService;
    @Override
    public OpenApi getById(Object... id) {
        return openApiDao.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OpenApi save(OpenApi openApi) {
        openApi.setAppId(idGenerateService.getId());
        openApi.setAppKey(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        if(openApi.getEntityType() == EntityType.USER){
            User user = userService.getById(openApi.getEntityId());
            if(user != null){
                openApi.setCustomerId(user.getCustomerId());
                openApi.setCustomerName(user.getCustomerName());
            }
        }else if(openApi.getEntityType() == EntityType.GROUP){
            Group group = groupService.getById(openApi.getEntityId());
            if(group != null){
                openApi.setCustomerId(group.getCustomerId());
                openApi.setCustomerName(group.getCustomerName());
            }
        }
        // 创建账户
        Account account = new Account();
        account.setAccountId(idGenerateService.getId());
        account.setAccountName(openApi.getAppName());
        account.setAccountBalance(BigDecimal.ZERO);
        account.setAccountType(openApi.getAccountType());
        account.setEntityType(EntityType.OPEN_API);
        account.setEntityId(openApi.getAppId());
        account.setEntityName(openApi.getAppName());
        account.setAccountLimit(BigDecimal.ZERO);
        account.setAccountLimitType(AccountLimitType.YEAR);
        account.setCreditLine(BigDecimal.ZERO);
        account.setCreatedTime(LocalDateTime.now());
        account.setCustomerId(openApi.getCustomerId());
        account.setCustomerName(openApi.getCustomerName());
        //设置主账户
        if(openApi.getAccountType() == AccountType.STAND_ALONE){
            account.setRelateAccountId(account.getAccountId());
            account.setRelateAccountName(account.getAccountName());
        }else{
            Account entityAccount = accountService.getByEntity(openApi.getEntityId());
            Account relateAccount = accountService.getTopStandAloneAccount(entityAccount);
            account.setRelateAccountId(relateAccount.getAccountId());
            account.setRelateAccountName(relateAccount.getRelateAccountName());
        }

        openApi.setAccountId(account.getAccountId());

        openApiDao.save(openApi);
        accountService.save(account);

        return openApi;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OpenApi update(OpenApi model) {
        OpenApi old = openApiDao.getById(model.getAppId());
        Account account = accountService.getById(old.getAccountId());
        boolean accountChanged = false;
        if(old.getAccountLimit().compareTo(model.getAccountLimit())!=0){
            account.setAccountLimit(model.getAccountLimit());
            accountChanged = true;
        }
        if(old.getAccountLimitType() != model.getAccountLimitType()){
            account.setAccountLimitType(model.getAccountLimitType());
            accountChanged = true;
        }
        if(!old.getAppName().equals(model.getAppName())){
            accountChanged = true;
        }
        model =  openApiDao.update(model);
        if(accountChanged) {
            accountService.update(account);
        }
        return model;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Object... id) {
        boolean success = openApiDao.deleteById(id);
        if(success) {
            accountService.deleteByEntity((Long)id[0]);
        }
        return success;
    }

    @Override
    public List<OpenApi> getByEntity(Long entityId) {
        return openApiDao.getByEntity(entityId);
    }

    @Override
    public List<OpenApi> getByPage(JSONObject condition,int pageNum, int pageSize) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        getWhere(condition,where,whereData);
        whereData.add(pageNum * pageSize);
        whereData.add(pageSize);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return  openApiDao.findList("select * from op_open_api where 1=1" + whereStr + " order by app_id desc limit ?,?",whereData.toArray());
    }

    @Override
    public Long count(JSONObject condition) {
        ArrayList<String> where = new ArrayList<>();
        ArrayList<Object> whereData = new ArrayList<>();
        getWhere(condition,where,whereData);
        String whereStr = !where.isEmpty() ? " and " + StringUtil.join(where.toArray()," and ") : "";
        return openApiDao.getJdbcTemplate().queryForObject("select count(1) from op_open_api where 1=1" + whereStr,Long.class,whereData.toArray());
    }

    private void getWhere(JSONObject condition, List<String> where, List<Object> whereData){
        if(condition.getLong("customerId") != null){
            where.add("customer_id = ?");
            whereData.add(condition.getLong("customerId"));
        }
        if(condition.getLong("entityId") != null){
            where.add("entity_id = ?");
            whereData.add(condition.getLong("entityId"));
        }
        if(condition.getLong("openApiType") != null){
            where.add("open_api_type = ?");
            whereData.add(condition.getLong("openApiType"));
        }
        if(condition.getLong("marketPerson") != null){
            List<Customer> customers = customerService.getByMarketPerson(condition.getLong("marketPerson"));
            if(customers.isEmpty()){
                where.add("customer_id = ?");
                whereData.add(-1);
            }else {
                ArrayList<Long> ids = new ArrayList<>();
                customers.forEach(customer -> ids.add(customer.getCustomerId()));
                where.add("customer_id in (" + StringUtil.join(ids.toArray(),",") +")");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAppStatus(Long appId, boolean enabled) {
        openApiDao.update("update op_open_api set status = ? where app_id = ?",enabled ? 1 : 0,appId);
    }

    @Override
    public List<OpenApi> getByCustomer(Long customerId) {
        return openApiDao.findList("select * from op_open_api where customer_id = ?",customerId);
    }

    @Override
    public List<OpenApi> deleteByCustomer(Long customerId) {
        List<OpenApi> openApiList = getByCustomer(customerId);
        if(!openApiList.isEmpty()) {
            openApiDao.update("delete from op_open_api where customer_id = ?", customerId);
            accountService.deleteByCustomer(customerId);
        }
        return openApiList;
    }

    @Override
    public List<OpenApi> deleteByEntity(Long entityId) {
        List<OpenApi> openApiList = getByEntity(entityId);
        if(!openApiList.isEmpty()) {
            openApiDao.update("delete from op_open_api where entity_id = ?", entityId);
            accountService.deleteByEntity(entityId);
        }
        return openApiList;
    }
}
