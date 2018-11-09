package com.elecredit.op.model;


import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 菜单
 * @author yangfei
 */
@Table(name = "op_adm_menu")
public class Menu extends BaseModel implements Serializable {
    /**
     * 菜单ID
     */
    @Id
    @Column(name = "menu_id")
    private Long menuId;
    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;
    /**
     * 父级菜单ID
     */
    @Column(name = "parent_id")
    private Long parentId;
    /**
     * 菜单图标css字符串
     */
    @Column(name = "menu_icon")
    private String menuIcon;

    /**
     * 路由
     */
    @Column(name = "route")
    private String route;

    /**
     * 排序号
     */
    @Column(name = "sort_number")
    private Integer sortNumber;


    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

}
