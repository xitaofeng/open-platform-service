package com.elecredit.op.service;

import com.elecredit.op.constants.EntityType;
import com.elecredit.op.constants.PermissionAuthType;
import com.elecredit.op.constants.PermissionType;
import com.elecredit.op.core.MessageQueueService;
import com.elecredit.op.dao.EntityPermissionDao;
import com.elecredit.op.dao.OpenApiDao;
import com.elecredit.op.model.EntityPermission;
import com.elecredit.op.model.OpenApi;
import com.elecredit.op.model.Permission;
import com.elecredit.user.constants.AuthType;
import com.elecredit.user.model.Group;
import com.elecredit.user.model.User;
import com.elecredit.user.service.GroupService;
import com.elecredit.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.elecredit.op.constants.MessageTopic.*;

/**
 * 实体权限配置服务实现类
 */
public class EntityPermissionServiceImpl implements EntityPermissionService {

    private static Logger logger = LoggerFactory.getLogger(EntityPermissionServiceImpl.class);

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
    @Autowired
    private PermissionService permissionService;
    @Override
    public EntityPermission getById(Object... id) {
        return entityPermissionDao.findOne("select * from op_permission_settings where entity_id = ? and permission_id = ?",id[0],id[1]);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EntityPermission save(EntityPermission entityPermission) {
        try {
            entityPermissionDao.save(entityPermission);
            notifyPermissionsSettingChanges(entityPermission);
        }catch (DuplicateKeyException e){

        }
        return entityPermission;
    }

    @Override
    public EntityPermission update(EntityPermission model) {
        EntityPermission updatedEntityPermission = entityPermissionDao.update(model);
        notifyPermissionsSettingChanges(updatedEntityPermission);
        notifyEntityPermissionUpdate(updatedEntityPermission);
        return updatedEntityPermission;
    }

    @Override
    public boolean deleteById(Object... id) {
        return entityPermissionDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(List<EntityPermission> newPermissionList, boolean deleteNotExist) {
        if(!newPermissionList.isEmpty()){
            Long entityId = newPermissionList.get(0).getEntityId();
            //已存在的权限列表
            List<EntityPermission> existPermissions = getByEntity(entityId);
            for(EntityPermission entityPermission : newPermissionList){
                if(existPermissions.contains(entityPermission)){
                    // do nothing
                }else {
                    entityPermissionDao.save(entityPermission);
                }
            }
            for(EntityPermission entityPermission : existPermissions){
                if(!newPermissionList.contains(entityPermission) && deleteNotExist){
                    entityPermissionDao.deleteById(entityId,entityPermission.getPermissionId());
                }else{
                    // do nothing
                }
            }
            notifyPermissionsSettingChanges(newPermissionList.get(0));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(List<EntityPermission> entityPermissionList) {
        save(entityPermissionList,false);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<EntityPermission> entityPermissionList) {
        for(EntityPermission entityPermission : entityPermissionList){
            entityPermissionDao.update(entityPermission);
        }
        notifyPermissionsSettingChanges(entityPermissionList.get(0));
    }

    @Override
    public List<EntityPermission> getByEntity(Long entityId) {
        return entityPermissionDao.findList("select a.* from op_permission_settings a,op_adm_permission b\n" +
                "where a.entity_id = ? and a.permission_id = b.permission_id\n" +
                "union all\n" +
                "select a.* from op_permission_settings a,op_service b\n" +
                "where a.entity_id = ? and a.permission_id = b.service_id " +
                "union all\n" +
                "select a.* from op_permission_settings a,op_service_api b\n" +
                "where a.entity_id = ? and a.permission_id = b.service_api_id",entityId,entityId,entityId);
    }

    @Override
    public List<EntityPermission> getAllByEntity(Long entityId, int entityType) {
        //用Set去重
        Set<EntityPermission> entityPermissions = new HashSet<>();
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
            List<List<EntityPermission>> groupPermissions = new ArrayList<>();
            for(int i = groupChain.size() -1; i>=0; i--){
                Group groupLoop = groupChain.get(i);
                groupPermissions.add(getByEntity(groupLoop.getGroupId()));
                if(groupLoop.getAuthType() == PermissionAuthType.STAND_ALONE){
                    break;
                }
            }
            // 由高到低，下级覆盖上级
            for(int i = groupPermissions.size() -1; i>=0; i--){
                entityPermissions.addAll(groupPermissions.get(i));
            }
        }
        return new ArrayList<>(entityPermissions);
    }

    @Override
    public EntityPermission getAllByEntityAndPermission(Long entityId, int entityType, Long permissionId) {
        EntityPermission entityPermission = getById(entityId,permissionId);
        if(entityPermission != null){
            return entityPermission;
        } else {
            if(entityType == EntityType.OPEN_API){
                OpenApi openApi = openApiDao.getById(entityId);
                if(openApi != null && openApi.getAuthType() == AuthType.SHARE) {
                    return getAllByEntityAndPermission(openApi.getEntityId(), openApi.getEntityType(), permissionId);
                }
            } else if(entityType == EntityType.USER){
                User user = userService.getById(entityId);
                if(user != null && user.getAuthType() == AuthType.SHARE){
                    return getAllByEntityAndPermission(user.getGroupId(),EntityType.GROUP,permissionId);
                }
            } else if(entityType == EntityType.GROUP){
                Group group = groupService.getById(entityId);
                if(group != null && group.getAuthType() == AuthType.SHARE && group.getParentId() != -1){
                    return getAllByEntityAndPermission(group.getParentId(),EntityType.GROUP,permissionId);
                }
            }
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<EntityPermission> entityPermissionList) {
        for(EntityPermission entityPermission : entityPermissionList){
            entityPermissionDao.delete(entityPermission);
        }
    }

    @Override
    public void clearByEntity(Long entityId) {
        entityPermissionDao.update("delete from op_permission_settings where entity_id = ?",entityId);
    }

    private void notifyEntityPermissionUpdate(EntityPermission entityPermission){
        messageQueueService.sendTopic(ENTITY_PERMISSION_UPDATE, entityPermission);
        System.out.println(ENTITY_PERMISSION_UPDATE);
    }
    private void notifyPermissionsSettingChanges(EntityPermission entityPermission){

        List<Group> groupList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        List<OpenApi> openApiList = new ArrayList<>();

        if(entityPermission.getEntityType() == EntityType.GROUP) {
            groupList = groupService.getAllByParent(entityPermission.getEntityId(),true);

            // Group下的所有用户
            userList = userService.findByGroup(entityPermission.getEntityId(),false);
            if(!userList.isEmpty()) {
                messageQueueService.sendTopic(USER_PERMISSION_UPDATE, userList);
            }

            // Group 下的所有OpenApi
            List<Long> entityIdList = groupList.stream().map(Group::getGroupId).collect(Collectors.toList());
            entityIdList.addAll(userList.stream().map(User::getUserId).collect(Collectors.toList()));
            openApiList = openApiDao.getByEntity(entityIdList);

        }else if(entityPermission.getEntityType() == EntityType.USER) {
            userList.add(userService.getById(entityPermission.getEntityId()));

            //用户下的所有OpenApi
            openApiList = openApiDao.getByEntity(entityPermission.getEntityId());
        }else if(entityPermission.getEntityType() == EntityType.OPEN_API) {
            OpenApi openApi = openApiDao.getById(entityPermission.getEntityId());
            openApiList = new ArrayList<>();
            openApiList.add(openApi);
        }

        for(Group group : groupList){
            messageQueueService.sendTopic(GROUP_PERMISSION_UPDATE, group);
        }

        for(User user : userList){
            messageQueueService.sendTopic(GROUP_PERMISSION_UPDATE, user);
        }

        for(OpenApi openApi : openApiList){
            messageQueueService.sendTopic(OPENAPI_PERMISSION_UPDATE, openApi);
        }
    }

    /**
     * 刷新默认权限
     */
    @Override
    public void refreshDefaultPermissions(){
        List<Permission> defaultPermissions = permissionService.getDefaultPermission();
        List<Group> groupList = groupService.getAllDefault();
        for(Group group : groupList){
            List<EntityPermission> entityPermissionList = new ArrayList<>();
            for(Permission permission : defaultPermissions){
                EntityPermission entityPermission = new EntityPermission();
                entityPermission.setPermissionType(PermissionType.SYSTEM);
                entityPermission.setPermissionId(permission.getPermissionId());
                entityPermission.setPermissionDetail("{}");
                entityPermission.setEntityType(EntityType.GROUP);
                entityPermission.setEntityId(group.getGroupId());
                entityPermissionList.add(entityPermission);
            }
            save(entityPermissionList,false);
        }
    }
}
