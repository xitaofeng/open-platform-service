package com.elecredit.op.model;

import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限菜单对应关系
 */
@Table(name = "op_adm_permission_menu")
public class PermissionMenu extends BaseModel implements Serializable {
    /**
     * 菜单ID
     */
    @Id
    @Column(name = "menu_id")
    private Long menuid;
    /**
     * 权限ID
     */
    @Id
    @Column(name = "permission_id")
    private Long permissionid;

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public Long getPermissionid() {
        return permissionid;
    }

    public void setPermissionid(Long permissionid) {
        this.permissionid = permissionid;
    }
}
