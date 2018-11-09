package com.elecredit.op.model;

import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author yangfei
 * 权限
 */
@Table(name = "op_adm_permission")
public class Permission extends BaseModel implements Serializable {
    /**
     * 权限ID
     */
    @Id
    @Column(name = "permission_id")
    private Long permissionId;
    /**
     * 权限名称
     */
    @Column(name = "permission_name")
    private String permissionName;
    /**
     * 权限类型 0 系统权限 1 服务权限
     */
    @Column(name = "permission_type")
    private Integer permissionType;
    /**
     * 权限标识
     */
    @Column(name = "flag")
    private String flag;
    /**
     * 上级ID
     */
    @Column(name = "parent_id")
    private Long parentId;
    /**
     * 排序号
     */
    @Column(name = "sort_num")
    private Integer sortNum;

    /**
     * 默认选中
     */
    @Column(name = "default_check")
    private Integer defaultCheck;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionId);
    }

    public Integer getDefaultCheck() {
        return defaultCheck;
    }

    public void setDefaultCheck(Integer defaultCheck) {
        this.defaultCheck = defaultCheck;
    }
}
