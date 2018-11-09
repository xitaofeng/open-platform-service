package com.elecredit.op.model;


import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 开放平台服务
 * @author yangfei
 */
@Table(name = "op_service")
public class DataService extends BaseModel implements Serializable {
    /**
     * 服务 ID
     */
    @Id
    @Column(name = "service_id")
    private Long serviceId;
    /**
     * 服务名称
     */
    @Column(name = "service_name")
    private String serviceName;
    /**
     * 上级服务ID,针对自定义服务，标准服务固定为0
     */
    @Column(name = "parent")
    private String parent;
    /**
     * 服务图标文件名，用于页面展示
     */
    @Column(name = "icon")
    private String icon;

    @Column(name = "batch_template")
    private String batchTemplate;
    /**
     * 服务说明，用于页面展示
     */
    @Column(name = "description")
    private String description;
    /**
     * 支持的计费方式,多个逗号分隔
     */
    @Column(name = "support_charge_type")
    private String supportChargeType;
    /**
     * 支付的计费模式
     */
    @Column(name = "support_charge_mode")
    private String supportChargeMode;
    /**
     * 支持的去重方式
     */
    @Column(name = "support_distinct_strategy")
    private String supportDistinctStrategy;
    /**
     * 终端价
     */
    @Column(name = "standard_price")
    private BigDecimal standardPrice;
    /**
     * 市场价
     */
    @Column(name = "common_price")
    private BigDecimal commonPrice;
    /**
     * 代理价
     */
    @Column(name = "agent_price")
    private BigDecimal agentPrice;
    /**
     * 类别
     */
    @Column(name = "category")
    private String category;
    /**
     * 排序号
     */
    @Column(name = "sort_number")
    private Integer sortNumber;
    /**
     * 产品功能
     */
    @Column(name = "product_info")
    private String productInfo;
    /**
     * 业务场景
     */
    @Column(name = "business_scenarios")
    private String businessScenarios;
    /**
     * 常见问题
     */
    @Column(name = "questions")
    private String questions;
    /**
     * 状态 1 正常，2 禁用
     */
    @Column(name = "status")
    private Integer status;
    /**
     * api列表
     */
    private List<DataServiceApi> apiList;

    /**
     *  渠道列表
     */
    private List<Supplier> supplierList;

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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupportChargeType() {
        return supportChargeType;
    }

    public void setSupportChargeType(String supportChargeType) {
        this.supportChargeType = supportChargeType;
    }

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public List<DataServiceApi> getApiList() {
        return apiList;
    }

    public void setApiList(List<DataServiceApi> apiList) {
        this.apiList = apiList;
    }

    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<Supplier> supplierList) {
        this.supplierList = supplierList;
    }

    public String getSupportChargeMode() {
        return supportChargeMode;
    }

    public void setSupportChargeMode(String supportChargeMode) {
        this.supportChargeMode = supportChargeMode;
    }

    public String getSupportDistinctStrategy() {
        return supportDistinctStrategy;
    }

    public void setSupportDistinctStrategy(String supportDistinctStrategy) {
        this.supportDistinctStrategy = supportDistinctStrategy;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getBusinessScenarios() {
        return businessScenarios;
    }

    public void setBusinessScenarios(String businessScenarios) {
        this.businessScenarios = businessScenarios;
    }

    public BigDecimal getCommonPrice() {
        return commonPrice;
    }

    public void setCommonPrice(BigDecimal commonPrice) {
        this.commonPrice = commonPrice;
    }

    public BigDecimal getAgentPrice() {
        return agentPrice;
    }

    public void setAgentPrice(BigDecimal agentPrice) {
        this.agentPrice = agentPrice;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBatchTemplate() {
        return batchTemplate;
    }

    public void setBatchTemplate(String batchTemplate) {
        this.batchTemplate = batchTemplate;
    }
}
