package com.elecredit.op.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 服务请求记录
 */
public class ServiceRequestRecord implements Serializable {
    /**
     * 请求流水号
     */
    private String requestSn;
    /**
     * 客户端请求流水号
     */
    private String clientRequestSn;
    /**
     * 开放平台openApi Id
     */
    private Long openApiId;
    /**
     * 开放平台openApi 名称
     */
    private String openApiName;
    /**
     * 请求主体ID
     */
    private Long entityId;
    /**
     * 实体名称
     */
    private String entityName;
    /**
     * 请求方体类型
     */
    private Integer entityType;
    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 接口ID
     */
    private Long apiId;
    /**
     *  接口名称
     */
    private String apiName;
    /**
     * 服务ID
     */
    private Long serviceId;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 任务ID
     */
    private Long taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 客户端请求IP
     */
    private String requestIp;
    /**
     * 调用时间
     */
    private LocalDateTime requestTime;

    /**
     *  消费金额
     */
    private BigDecimal spend;
    /**
     *  成本
     */
    private BigDecimal cost;
    /**
     * 请求参数
     */
    private Object params;
    /**
     * 请求结果
     */
    private Object result;

    public String getRequestSn() {
        return requestSn;
    }

    public void setRequestSn(String requestSn) {
        this.requestSn = requestSn;
    }

    public Long getOpenApiId() {
        return openApiId;
    }

    public void setOpenApiId(Long openApiId) {
        this.openApiId = openApiId;
    }

    public String getOpenApiName() {
        return openApiName;
    }

    public void setOpenApiName(String openApiName) {
        this.openApiName = openApiName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
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

    public String getClientRequestSn() {
        return clientRequestSn;
    }

    public void setClientRequestSn(String clientRequestSn) {
        this.clientRequestSn = clientRequestSn;
    }

    public BigDecimal getSpend() {
        return spend;
    }

    public void setSpend(BigDecimal spend) {
        this.spend = spend;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
