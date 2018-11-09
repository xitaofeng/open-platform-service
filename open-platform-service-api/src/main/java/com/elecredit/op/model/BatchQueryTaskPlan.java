package com.elecredit.op.model;

import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by zhaoyou
 * Date:2018/10/9
 * Time:9:08
 */
@Table(name = "op_batch_query_task_plan")
public class BatchQueryTaskPlan extends BaseModel implements Serializable{
    /**
     * 记录Id
     */
    @Id
    @Column(name="plan_id")
    private Long planId;
    /**
     * 任务Id
     */
    @Column(name="task_id")
    private Long taskId;
    /**
     * 任务名称
     */
    @Column(name="task_name")
    private String taskName;
    /**
     * 实体名称
     */
    @Column(name = "entity_name")
    private String entityName;
    /**
     * 实体ID
     */
    @Column(name = "entity_id")
    private Long entityId;
    /**
     * 实体类型 1用户，2用户组,3 openAPI
     */
    @Column(name = "entity_type")
    private Integer entityType;
    /**
     * 客户名称
     */
    @Column(name = "customer_name")
    private String customerName;
    /**
     * 客户ID
     */
    @Column(name = "customer_id")
    private Long customerId;
    /**
     * 账户ID
     */
    @Column(name = "account_id")
    private Long accountId;
    /**
     * 账户名称
     */
    @Column(name = "account_name")
    private String accountName;
    /**
     * 服务 ID
     */
    @Column(name = "service_id")
    private Long serviceId;
    /**
     * 服务名称
     */
    @Column(name = "service_name")
    private String serviceName;
    /**
     * 查询参数
     */
    @Column(name = "params")
    private String params;
    /**
     * 状态 1 等待处理，2 正在处理 3 成功 4 无结果 5失败
     */
    @Column(name = "status")
    private Integer status;
    /**
     * 结果ID
     */
    @Column(name = "result_id")
    private String resultId;
    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    /**
     * 完成时间
     */
    @Column(name = "complete_time")
    private LocalDateTime completeTime;

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
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

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(LocalDateTime completeTime) {
        this.completeTime = completeTime;
    }
}
