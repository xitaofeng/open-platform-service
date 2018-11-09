package com.elecredit.op.model;

import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 开放平台访问接口
 */
@Table(name = "op_open_api")
public class OpenApi  extends BaseModel implements Serializable {
    /**
     * API ID
     */
    @Id
    @Column(name = "app_id")
    private Long appId;
    /**
     * api 密钥
     */
    @Column(name = "app_key")
    private String appKey;
    /**
     * API 名称
     */
    @Column(name = "app_name")
    private String appName;
    /**
     * 实体ID
     */
    @Column(name = "entity_id")
    private Long entityId;
    /**
     * 实体名称
     */
    @Column(name = "entity_name")
    private String entityName;
    /**
     * 客户ID
     */
    @Column(name = "customer_id")
    private Long customerId;
    /**
     * 客户名称
     */
    @Column(name = "customer_name")
    private String customerName;
    /**
     * 实体类型，1 group,2 user
     */
    @Column(name = "entity_type")
    private Integer entityType;
    /**
     * 账户类型，1共享账户，2独立账户
     */
    @Column(name = "account_type")
    private int accountType;
    /**
     * 限定额度
     */
    @Column(name = "account_limit")
    private BigDecimal accountLimit;
    /**
     * 额度限定类型 1 年，2 月，3 日
     */
    @Column(name = "account_limit_type")
    private Integer accountLimitType;
    /**
     * 授权方式，1共享，2独立
     */
    @Column(name = "auth_type")
    private int authType;
    /**
     * 状态, 1启用，2禁用
     */
    @Column(name = "status")
    private int status;
    /**
     * 默认账户ID
     */
    @Column(name = "account_id")
    private Long accountId;
    /**
     * OpenAPI类型
     * 1 正式 2 测试
     */
    @Column(name = "open_api_type")
    private Integer openApiType;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(BigDecimal accountLimit) {
        this.accountLimit = accountLimit;
    }

    public Integer getAccountLimitType() {
        return accountLimitType;
    }

    public void setAccountLimitType(Integer accountLimitType) {
        this.accountLimitType = accountLimitType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getOpenApiType() {
        return openApiType;
    }

    public void setOpenApiType(Integer openApiType) {
        this.openApiType = openApiType;
    }
}
