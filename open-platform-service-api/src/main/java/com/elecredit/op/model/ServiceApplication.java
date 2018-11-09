package com.elecredit.op.model;


import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 服务申请记录
 * @author yangfei
 */
@Table(name = "op_service_application")
public class ServiceApplication extends BaseModel implements Serializable {
    /**
     * 申请记录ID
     */
    @Id
    @Column(name = "application_id")
    private Long applicationId;
    /**
     * 服务ID
     */
    @Column(name = "service_id")
    private Long serviceId;
    /**
     * 服务名称
     */
    @Column(name = "service_name")
    private String serviceName;
    /**
     * 申请人姓名
     */
    @Column(name = "username")
    private String username;
    /**
     * 申请人ID
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 申请时间
     */
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;
    /**
     * 处理人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;
    /**
     * 处理人ID
     */
    @Column(name = "operator_id")
    private Long operatorId;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 处理时间
     */
    @Column(name = "operate_time")
    private LocalDateTime operateTime;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(LocalDateTime operateTime) {
        this.operateTime = operateTime;
    }
}