package com.elecredit.op.service;

import com.elecredit.op.model.Supplier;

import java.util.List;

/**
 * 渠道服务
 * @author wangqi
 */
public interface SupplierService {
    /**
     * 根据渠道 ID 查找渠道
     * @param id
     * @return
     */
    Supplier getById(Long id);

    /**
     * 根据渠道 ID 查找渠道
     * @param id
     * @param withDataService      是否附加服务列表
     * @return
     */
    Supplier getById(Long id, boolean withDataService);

    /**
     * 获取所有渠道信息
     * @param
     * @return
     */
    List<Supplier> getAll();

    /**
     * 获取所有渠道信息
     * @param withDataService       是否附加服务列表
     * @return
     */
    List<Supplier> getAll(boolean withDataService);

    /**
     * 根据渠道名查找渠道
     * @param name
     * @return
     */
    Supplier getByName(String name);

    /**
     * 保存渠道
     * @param supplier
     * @return
     */
    Supplier save(Supplier supplier);

    /**
     * 更新渠道
     * @param supplier
     * @return
     */
    Supplier update(Supplier supplier);
    /**
     * 删除渠道
     * @param supplier
     * @return
     */
    boolean delete(Supplier supplier);

    /**
     * 根据 ID 删除渠道
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 获取第N页的渠道列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Supplier> getByPage(int pageNum, int pageSize);

    /**
     * 获取第N页的渠道列表
     * @param pageNum
     * @param pageSize
     * @param withDataService  是否附加服务列表
     * @return
     */
    List<Supplier> getByPage(int pageNum, int pageSize, boolean withDataService);
    /**
     * 返回记录总数
     * @return
     */
    Long count();
}
