package com.elecredit.op.model;

import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by zhaoyou
 * Date:2018/10/9
 * Time:9:08
 */
@Table(name = "op_batch_query_task")
public class BatchQueryTask extends BaseModel implements Serializable{
    /**
     * 任务Id
     */
    @Id
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
     * 提交时间
     */
    @Column(name = "created_time")
    private LocalDateTime createdTime;
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
     * 状态 1 等待处理，2 正在处理 3 已暂停 4 已取消 5完成
     */
    @Column(name = "status")
    private Integer status;
    /**
     * 总记录数
     */
    @Column(name = "total_records")
    private Integer totalRecords;
    /**
     * 百分比
     */
    @Column(name = "percentage")
    private Float percentage;
    /**
     * 成功条数
     */
    @Column(name = "success_count")
    private Integer successCount;
    /**
     * 无结果条数
     */
    @Column(name = "no_result_count")
    private Integer noResultCount;
    /**
     * 失败条数
     */
    @Column(name = "fail_count")
    private Integer failCount;
    /**
     * 完成时间
     */
    @Column(name = "complete_time")
    private LocalDateTime completeTime;
    /**
     * 文件名
     */
    @Column(name = "file")
    private String file;
    private List<BatchQueryTaskPlan> planList;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getNoResultCount() {
        return noResultCount;
    }

    public void setNoResultCount(Integer noResultCount) {
        this.noResultCount = noResultCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public LocalDateTime getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(LocalDateTime completeTime) {
        this.completeTime = completeTime;
    }

    public List<BatchQueryTaskPlan> getPlanList() {
        return planList;
    }

    public void setPlanList(List<BatchQueryTaskPlan> planList) {
        this.planList = planList;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }
}
