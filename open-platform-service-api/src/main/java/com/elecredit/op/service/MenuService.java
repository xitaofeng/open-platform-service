package com.elecredit.op.service;

import com.elecredit.op.model.Menu;

import java.util.List;

/**
 * 菜单服务
 * @author yangfei
 */
public interface MenuService {
    /**
     * 根据菜单 ID 查找菜单
     * @param id
     * @return
     */
    Menu getById(Long id);

    /**
     * 根据菜单名查找菜单
     * @param menuName
     * @return
     */
    Menu getByMenuName(String menuName);

    /**
     * 查找所有菜单
     * @param
     * @return
     */
    List<Menu> getAll();

    /**
     * 根据userId查找所有菜单
     * @param
     * @return
     */
    List<Menu> getByUser(Long userId);

    /**
     * 根据permissionId查找所有菜单
     * @param
     * @return
     */
    List<Menu> getByPermissions(List<Long> permissions);

    /**
     * 根据菜单ID查找所有下级子菜单
     * @param id
     * @return
     */
    List<Menu> getChildrenByParent(Long id);

    /**
     * 保存菜单
     * @param menu
     * @return
     */
    Menu save(Menu menu);

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    Menu update(Menu menu);

    /**
     * 更新菜单
     * @param menuList
     * @return
     */
    boolean update(List<Menu> menuList);

    /**
     * 删除菜单
     * @param menu
     * @return
     */
    boolean delete(Menu menu);

    /**
     * 根据 ID 删除菜单
     * @param id
     * @return
     */
    boolean deleteById(Long id);

}
