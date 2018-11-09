package com.elecredit.op.model;

import com.elecredit.common.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * 用户权限映射
 */
@Table(name = "op_permission_settings")
public class EntityPermission extends BaseModel implements Serializable {
    /**
     * 节点ID
     */
    @Id
    @Column(name = "entity_id")
    private Long entityId;
    /**
     * 权限ID
     */
    @Id
    @Column(name = "permission_id")
    private Long permissionId;
    /**
     * 节点类型
     */
    @Column(name = "entity_type")
    private int entityType;
    /**
     * 权限类型
     */
    @Column(name = "permission_type")
    private int permissionType;

    /**
     * 权限详情
     */
    @Column(name = "permission_detail")
    private String permissionDetail;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionDetail() {
        return permissionDetail;
    }

    public void setPermissionDetail(String permissionDetail) {
        this.permissionDetail = permissionDetail;
    }

    public int getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(int permissionType) {
        this.permissionType = permissionType;
    }

    @Override
    public int hashCode() {
        return (this.entityId + "" + this.permissionId).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EntityPermission that = (EntityPermission) obj;
        return Objects.equals(permissionId, that.permissionId) && Objects.equals(entityId, that.entityId);
    }
}
