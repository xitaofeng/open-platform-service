package com.elecredit.op.service;

import com.elecredit.op.model.EntityPermission;
import com.elecredit.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实体权限配置服务
 */
public interface EntityPermissionService extends BaseService<EntityPermission> {

    /**
     * 保存用户权限列表
     * @param entityPermissionList
     * @param deleteNotExist 是否删除不存在的权限
     * @return
     */
    void save(List<EntityPermission> entityPermissionList, boolean deleteNotExist);

    /**
     * 保存用户权限列表，不删除不存在的权限
     * @param entityPermissionList
     */
    void save(List<EntityPermission> entityPermissionList);

    /**
     * 更新权限配置
     * @param entityPermissionList
     */
    @Transactional(rollbackFor = Exception.class)
    void update(List<EntityPermission> entityPermissionList);

    /**
     * 获取实体权限配置
     * @param entityId
     * @return
     */
    List<EntityPermission> getByEntity(Long entityId);

    /**
     * 获取所有实体权限配置，包括上级拥有的权限
     * @param entityId
     * @param entityType 实体类型
     */
    public List<EntityPermission> getAllByEntity(Long entityId, int entityType);

    /**
     *
     * @param entityId
     * @param entityType
     * @param permissionId
     * @return
     */
    public EntityPermission getAllByEntityAndPermission(Long entityId, int entityType,Long permissionId);
    /**
     * 删除用户权限
     * @param entityPermissionList
     */
    void delete(List<EntityPermission> entityPermissionList);

    /**
     * 清空实体权限配置
     * @param entityId
     */
    void clearByEntity(Long entityId);

    void refreshDefaultPermissions();
}
