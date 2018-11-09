package com.elecredit.op.model;

import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 渠道-服务配置
 */
@Table(name = "op_supplier_service")
public class SupplierServiceConfig extends BaseModel implements Serializable {
    /**
     * 渠道ID
     */
    @Id
    @Column(name = "supplier_id")
    private Long supplierId;
    /**
     * 服务ID
     */
    @Id
    @Column(name = "service_id")
    private Long serviceId;
    /**
     * 接入价，成本价
     */
    @Column(name = "cost")
    private BigDecimal cost;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
