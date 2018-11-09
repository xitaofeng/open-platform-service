package com.elecredit.op.service;

import com.elecredit.op.model.Supplier;
import com.elecredit.op.model.SupplierServiceConfig;

import java.util.List;

/**
 * 渠道-服务配置服务
 * @author wangqi
 */
public interface SupplierServiceConfigService {

    /**
     * 根据服务id获取渠道-服务配置信息
     */
    List<SupplierServiceConfig> getByServiceId(Long serviceId);

    /**
     * 根据渠道id获取渠道-服务配置信息
     */
    List<SupplierServiceConfig> getBySupplierId(Long supplierId);

    /**
     * 更新渠道-服务配置
     * @param serviceId
     * @param supplierIdList
     * @return
     */
    boolean updateSupplierList(Long serviceId,List<Long> supplierIdList);

    /**
     * 更新渠道-服务配置
     * @param supplierId
     * @param dataServiceIdList
     * @return
     */
    boolean updateServiceList(Long supplierId,List<Long> dataServiceIdList);

    /**
     * 获取第N页的渠道-服务配置列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Supplier> getByPage(int pageNum, int pageSize);

    /**
     * 返回记录总数
     * @return
     */
    Long count();
}
