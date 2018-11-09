package com.elecredit.op.service;

import com.elecredit.op.model.Permission;
import com.elecredit.service.BaseService;

import java.util.List;

/**
 * 系统权限服务
 */
public interface PermissionService extends BaseService<Permission> {

    /**
     * 获取所有权限列表
     * @return
     */
    List<Permission> getAll();

    /**
     * 批量更新权限
     * @param permissions
     */
    void batchUpdate(List<Permission> permissions);
    /**
     * 获取子权限
     * @param parentId
     * @return
     */
    List<Permission> getByParent(Long parentId);

    /**
     * 获取用户权限
     * @param entityId
     */
    List<Permission> getByEntity(Long entityId);

    /**
     * 获取实体权限
     * @param entityId
     */
    List<Permission> getAllByEntity(Long entityId);
    /**
     * 获取实体权限
     * @param entityId
     * @param entityType 实体类型
     */
    List<Permission> getAllByEntity(Long entityId, int entityType);

    /**
     * 获取默认权限
     */
    List<Permission> getDefaultPermission();
}
