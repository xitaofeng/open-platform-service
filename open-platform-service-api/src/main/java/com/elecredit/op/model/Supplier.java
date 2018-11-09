package com.elecredit.op.model;

import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * 渠道
 */
@Table(name = "op_supplier")
public class Supplier extends BaseModel implements Serializable {

    /**
     * 渠道ID
     */
    @Id
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 渠道名称
     */
    @Column(name = "supplier_name")
    private String supplierName;

    /**
     * 联系人
     */
    @Column(name = "contact")
    private String contact;

    /**
     * 联系人电话
     */
    @Column(name = "telephone")
    private String telephone;

    /**
     * 电子邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private String createdTime;

    /**
     * 服务列表
     */
    private List<DataService> serviceList;



    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {

        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContact() {

        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {

        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }


    public List<DataService> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<DataService> serviceList) {
        this.serviceList = serviceList;
    }
}
