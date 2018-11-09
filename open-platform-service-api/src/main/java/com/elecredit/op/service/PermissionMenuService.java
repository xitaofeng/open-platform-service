package com.elecredit.op.service;

import com.elecredit.op.model.PermissionMenu;

import java.util.List;

/**
 * 权限菜单对应关系服务
 * @author yangfei
 */
public interface PermissionMenuService {


    /**
     * 保存权限菜单对应关系
     * @param permissionMenuList
     * @return
     */
    boolean save(List<PermissionMenu> permissionMenuList);

    /**
     * 根据权限ID删除权限菜单对应关系
     * @param permission_id
     * @return
     */
    boolean deleteByPermissionId(Long permission_id);


}
