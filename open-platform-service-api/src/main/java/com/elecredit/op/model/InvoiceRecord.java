package com.elecredit.op.model;

import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 开票记录
 */
@Table(name = "off_invoice_record")
public class InvoiceRecord extends BaseModel implements Serializable {

    /**
     * 开票记录ID
     */
    @Id
    @Column(name = "invoice_id")
    private Long invoiceId;

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
     * 发票金额
     */
    @Column(name = "invoice_value")
    private String invoiceValue;

    /**
     * 收件人
     */
    @Column(name = "recipients")
    private String recipients;
    /**
     * 收件人电话
     */
    @Column(name = "recipients_phone")
    private String recipientsPhone;
    /**
     * 收件地址
     */
    @Column(name = "delivery_address")
    private String deliveryAddress;
    /**
     * 邮编
     */
    @Column(name = "zip_code")
    private String zipCode;
    /**
     * 快递公司名称
     */
    @Column(name = "express_name")
    private String expressName;
    /**
     * 快递单号
     */
    @Column(name = "express_no")
    private String expressNo;
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

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long paymentId) {
        this.invoiceId = paymentId;
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

    public String getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(String invoiceValue) {
        this.invoiceValue = invoiceValue;
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getRecipientsPhone() {
        return recipientsPhone;
    }

    public void setRecipientsPhone(String recipientsPhone) {
        this.recipientsPhone = recipientsPhone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }
}
