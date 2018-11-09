package com.elecredit.op.model;


import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 开放平台服务接口
 * @author yangfei
 */
@Table(name = "op_service_api")
public class ServiceApi extends BaseModel implements Serializable {
    /**
     * 接口ID
     */
    @Id
    @Column(name = "service_api_id")
    private Long serviceApiId;
    /**
     * 接口名称
     */
    @Column(name = "service_api_name")
    private String serviceApiName;
    /**
     * 服务ID
     */
    @Column(name = "service_id")
    private Long serviceId;
    /**
     * 服务接口说明，用于页面展示
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
     * 标准价格
     */
    @Column(name = "standard_price")
    private BigDecimal standardPrice;
    /**
     * 请求参数
     */
    @Column(name = "request_params")
    private String requestParams;
    /**
     * 返回字段
     */
    @Column(name = "response_fields")
    private String responseFields;
    /**
     * 响应代码，JsonArray,用于界面展示
     */
    @Column(name = "response_codes")
    private String responseCodes;
    /**
     * 正常响应时间，毫秒
     */
    @Column(name = "normal_duration")
    private Integer normalDuration;
    /**
     * 比较方式，1高于，-1低于，0 等于
     */
    @Column(name = "compare_type")
    private Integer compareType;
    /**
     * 阈值单位，1百分比，2固定值
     */
    @Column(name = "alert_value_unit")
    private Integer alertValueUnit;
    /**
     * 预警阈值
     */
    @Column(name = "alert_value")
    private Integer alertValue;
    /**
     * 是否预警，1是，0否
     */
    @Column(name = "is_alert")
    private Integer isAlert;
    /**
     * 排序号
     */
    @Column(name = "sort_number")
    private Integer sortNumber;
    /**
     * api地址
     */
    @Column(name = "api_url")
    private String apiUrl;
    /**
     * 请求方式 post,get
     */
    @Column(name = "request_type")
    private String requestType;
    /**
     * 请求格式 json
     */
    @Column(name = "request_content_type")
    private String requestContentType;
    /**
     * 响应格式 json
     */
    @Column(name = "response_content_type")
    private String responseContentType;
    /**
     * 请求示例
     */
    @Column(name = "request_examples")
    private String requestExamples;
    /**
     * 响应示例
     */
    @Column(name = "response_examples")
    private String responseExamples;

    public Long getServiceApiId() {
        return serviceApiId;
    }

    public void setServiceApiId(Long serviceApiId) {
        this.serviceApiId = serviceApiId;
    }

    public String getServiceApiName() {
        return serviceApiName;
    }

    public void setServiceApiName(String serviceApiName) {
        this.serviceApiName = serviceApiName;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getResponseFields() {
        return responseFields;
    }

    public void setResponseFields(String responseFields) {
        this.responseFields = responseFields;
    }

    public String getResponseCodes() {
        return responseCodes;
    }

    public void setResponseCodes(String responseCodes) {
        this.responseCodes = responseCodes;
    }

    public Integer getNormalDuration() {
        return normalDuration;
    }

    public void setNormalDuration(Integer normalDuration) {
        this.normalDuration = normalDuration;
    }

    public Integer getCompareType() {
        return compareType;
    }

    public void setCompareType(Integer compareType) {
        this.compareType = compareType;
    }

    public Integer getAlertValueUnit() {
        return alertValueUnit;
    }

    public void setAlertValueUnit(Integer alertValueUnit) {
        this.alertValueUnit = alertValueUnit;
    }

    public Integer getAlertValue() {
        return alertValue;
    }

    public void setAlertValue(Integer alertValue) {
        this.alertValue = alertValue;
    }

    public Integer getIsAlert() {
        return isAlert;
    }

    public void setIsAlert(Integer isAlert) {
        this.isAlert = isAlert;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestContentType() {
        return requestContentType;
    }

    public void setRequestContentType(String requestContentType) {
        this.requestContentType = requestContentType;
    }

    public String getResponseContentType() {
        return responseContentType;
    }

    public void setResponseContentType(String responseContentType) {
        this.responseContentType = responseContentType;
    }

    public String getRequestExamples() {
        return requestExamples;
    }

    public void setRequestExamples(String requestExamples) {
        this.requestExamples = requestExamples;
    }

    public String getResponseExamples() {
        return responseExamples;
    }

    public void setResponseExamples(String responseExamples) {
        this.responseExamples = responseExamples;
    }
}