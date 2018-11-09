package com.elecredit.op.service;

import com.elecredit.op.exception.ServiceApiIdAlreadyExistException;
import com.elecredit.op.model.DataServiceApi;

import java.util.List;

/**
 *  服务API
 * @author yangfei
 */
public interface DataServiceApiService {
    /**
     * 根据ID查找服务API
     * @param id
     * @return
     */
    DataServiceApi getById(Long id);

    /**
     * 获取指定服务的API
     * @param serviceId
     * @return
     */
    List<DataServiceApi> getByServiceId(Long serviceId);

    /**
     * 获取所有服务API
     * @param
     * @return
     */
    List<DataServiceApi> getAll();


    /**
     * 保存服务API
     * @param dataServiceApi
     * @return 保存成功返回 true, 失败返回false
     */
    DataServiceApi save(DataServiceApi dataServiceApi) throws ServiceApiIdAlreadyExistException;

    /**
     * 更新服务API
     * @param dataServiceApi
     * @return 更新成功返回 true, 失败返回false
     */
    DataServiceApi update(DataServiceApi dataServiceApi);
    /**
     * 删除服务API
     * @param dataServiceApi
     * @return 删除成功返回 true, 失败返回false
     */
    boolean delete(DataServiceApi dataServiceApi);

    /**
     * 根据ID删除服务API
     * @param id
     * @return 删除成功返回 true, 失败返回false
     */
    boolean deleteById(Long id);

    /**
     * 查找用户服务API
     * @param userId
     * @param key
     * @return
     */
    List<DataServiceApi> getByUser(Long userId,  String key);

    /**
     * 更新服务接口顺序
     * @param dataServiceApiList
     * @return
     */
    boolean updateOrder(List<DataServiceApi> dataServiceApiList);
}
