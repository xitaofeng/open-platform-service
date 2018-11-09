package com.elecredit.op.service;

import com.alibaba.fastjson.JSONObject;
import com.elecredit.op.exception.ServiceIdAlreadyExistException;
import com.elecredit.op.model.DataService;

import java.util.List;

/**
 *  服务
 * @author yangfei
 */
public interface DataServiceService {

    /**
     * 根据ID查找服务
     * @param id
     * @return
     */
    DataService getById(Long id);
    /**
     * 根据ID查找服务
     * @param id
     * @param withApi  是否附加接口列表
     * @return
     */
    DataService getById(Long id,boolean withApi,boolean withSupplier);

    /**
     * 获取所有服务
     * @param
     * @return
     */
    List<DataService> getAll();

    /**
     * 获取所有服务
     * @param withApi         是否附加接口列表
     * @param withSupplier    是否附加渠道列表
     * @return
     */
    List<DataService> getAll(boolean withApi, boolean withSupplier);


    /**
     * 根据标准服务ID查找子服务(自定义服务)，只返回直属子服务
     * @param parentId
     * @return
     */
    List<DataService> getByParent(Long parentId);
    /**
     * 根据标准服务ID查找所有子服务，不包含标准服务
     * @param parentId
     * @return
     */
    List<DataService> getAllByParent(Long parentId);

    /**
     * 根据标准服务ID查找子服务
     * @param parentId
     * @param containSelf 是否包括本节点，即传入的parent
     * @return
     */
    List<DataService> getAllByParent(Long parentId, boolean containSelf);
    /**
     * 保存服务
     * @param dataService
     * @return 保存成功返回 true, 失败返回false
     */
    DataService save(DataService dataService) throws ServiceIdAlreadyExistException;

    /**
     * 更新服务
     * @param dataService
     * @return 更新成功返回 true, 失败返回false
     */
    DataService update(DataService dataService);
    /**
     * 删除服务
     * @param dataService
     * @return 删除成功返回 true, 失败返回false
     */
    boolean delete(DataService dataService);

    /**
     * 根据ID删除服务
     * @param id
     * @return 删除成功返回 true, 失败返回false
     */
    boolean deleteById(Long id);

    /**
     * 获取服务分类列表
     * @return 删除成功返回 true, 失败返回false
     */
    List<String> categoryList();

    /**
     * 合并服务类别
     * @param categoryList
     * @param name
     * @return
     */
    boolean categoryMerge(List<String> categoryList,String name);

    /**
     * 查找用户服务
     * @return
     */
    List<DataService> getByUser(JSONObject condition);

    /**
     * 获取第N页的服务列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DataService> getByPage(JSONObject condition, int pageNum, int pageSize);

    /**
     * 返回记录总数
     * @return
     */
    Long count(JSONObject condition);

    /**
     * 更新服务顺序
     * @param dataServiceList
     * @return
     */
    boolean updateOrder(List<DataService> dataServiceList);
}
