package com.elecredit.op.model;

import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 客户回款记录
 */
@Table(name = "off_payment_record")
public class PaymentRecord extends BaseModel implements Serializable {

    /**
     * 回款记录ID
     */
    @Id
    @Column(name = "payment_id")
    private Long paymentId;

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
     * 回款金额
     */
    @Column(name = "pay_amount")
    private String payAmount;

    /**
     * 回款时间
     */
    @Column(name = "pay_time")
    private LocalDate payTime;
    /**
     * 回款状态 1已到款 2 未到款
     */
    private Integer status;
    /**
     * 操作人
     */
    @Column(name = "operator")
    private String operator;
    /**
     * 操作人ID
     */
    @Column(name = "operator_id")
    private Long operatorId;
    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
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

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public LocalDate getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDate payTime) {
        this.payTime = payTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
}
