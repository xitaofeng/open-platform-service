package com.elecredit.op.service;

import com.elecredit.op.constants.EntityType;
import com.elecredit.op.constants.PermissionAuthType;
import com.elecredit.op.core.MessageQueueService;
import com.elecredit.op.dao.EntityPermissionDao;
import com.elecredit.op.dao.OpenApiDao;
import com.elecredit.op.dao.PermissionDao;
import com.elecredit.op.model.OpenApi;
import com.elecredit.op.model.Permission;
import com.elecredit.user.model.Group;
import com.elecredit.user.model.User;
import com.elecredit.user.service.GroupService;
import com.elecredit.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 权限服务实现类
 */
public class PermissionServiceImpl implements PermissionService {

    private static Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private EntityPermissionDao entityPermissionDao;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private OpenApiDao openApiDao;
    @Autowired
    private MessageQueueService messageQueueService;

    @Override
    public Permission getById(Object... id) {
        return permissionDao.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Permission save(Permission model) {
        model.setSortNum(9999);
        permissionDao.save(model);
        return model;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Permission update(Permission model) {
        return permissionDao.update(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Object... id) {
        List<Permission> permissions = permissionDao.findList("select * from op_adm_permission where parent_id=?",id);
        if(permissions != null) {
            for (Permission permission : permissions) {
                deleteById(permission.getPermissionId());
            }
        }
        return permissionDao.deleteById(id);
    }

    @Override
    public List<Permission> getAll() {
        return permissionDao.findList("select * from op_adm_permission order by sort_num,permission_id");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(List<Permission> permissions) {
        for(Permission permission : permissions){
            permissionDao.update(permission);
        }
    }

    @Override
    public List<Permission> getByParent(Long parentId) {
        return permissionDao.findList("select * from op_adm_permission where parent_id = ? order by sort_num,permission_id",parentId);
    }


    @Override
    public List<Permission> getByEntity(Long entityId) {
        return permissionDao.findList("select b.* from op_permission_settings a,op_adm_permission b\n" +
                "where a.entity_id = ? and a.permission_id = b.permission_id",entityId);
    }

    @Override
    public List<Permission> getAllByEntity(Long entityId) {

        OpenApi openApi = openApiDao.getById(entityId);

        if(openApi != null) {
            return getAllByEntity(entityId, EntityType.OPEN_API);
        }

        User user = userService.getById(entityId);

        if(user != null){
            return getAllByEntity(entityId,EntityType.USER);
        }

        Group group = groupService.getById(entityId);

        if(group != null){
            return getAllByEntity(entityId,EntityType.GROUP);
        }

        return new ArrayList<>();
    }

    @Override
    public List<Permission> getAllByEntity(Long entityId, int entityType) {
        //用Set去重
        Set<Permission> entityPermissions = new HashSet<>();
        if(entityType == EntityType.USER) {
            User user = userService.getById(entityId);
            if(user == null){
                return Collections.EMPTY_LIST;
            }
            if(user.getAuthType() == PermissionAuthType.SHARE) {
                entityPermissions.addAll(getAllByEntity(user.getGroupId(),EntityType.GROUP));
            }
            // 用户的权限
            entityPermissions.addAll(getByEntity(user.getUserId()));
        }else if(entityType == EntityType.OPEN_API){
            OpenApi openApi = openApiDao.getById(entityId);
            if(openApi == null){
                return Collections.EMPTY_LIST;
            }
            if(openApi.getAuthType() == PermissionAuthType.SHARE){
                entityPermissions.addAll(getAllByEntity(openApi.getEntityId(),openApi.getEntityType()));
            }
            // OpenAPI的权限
            entityPermissions.addAll(getByEntity(entityId));
        }else{
            //逐级向上找机构权限，上级机构授权方式为独立则中断查找
            List<Group> groupChain = groupService.getParentChainByEntity(entityId,EntityType.GROUP);
            for(int i = groupChain.size()-1;i>=0;i--){
                Group groupLoop = groupChain.get(i);
                entityPermissions.addAll(getByEntity(groupLoop.getGroupId()));
                if(groupLoop.getAuthType() == PermissionAuthType.STAND_ALONE){
                    break;
                }
            }
        }
        return new ArrayList<>(entityPermissions);
    }

    @Override
    public List<Permission> getDefaultPermission() {
        return permissionDao.findList("select * from op_adm_permission where default_check = 1 order by sort_num,permission_id");
    }

    /**
     * 查找节点配置的权限
     * @param entityId
     * @param nodeType
     * @return
     */
    public List<Permission> getEntityPermissions(Long entityId, int nodeType){
        return permissionDao.findList("select * from op_permission_settings a,op_adm_permission b where a.entity_id = ? and node_type = ? and a.permission_id = b.permission_id order by sort_num,b.permission_id",entityId, nodeType);
    }
}