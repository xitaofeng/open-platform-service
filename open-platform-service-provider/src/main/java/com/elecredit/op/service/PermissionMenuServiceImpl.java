package com.elecredit.op.service;

import com.elecredit.op.dao.PermissionMenuDao;
import com.elecredit.op.model.PermissionMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜单服务实现类
 */
@Component
public class PermissionMenuServiceImpl implements PermissionMenuService {
    @Autowired
    private PermissionMenuDao permissionMenuDao;
    @Autowired
    private PermissionMenuService permissionMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(List<PermissionMenu> permissionMenuList) {

        if(!permissionMenuList.isEmpty()) {
            Long permissionId = permissionMenuList.get(0).getPermissionid();
            deleteByPermissionId(permissionId);

            for (PermissionMenu permissionMenu : permissionMenuList) {
                permissionMenuDao.save(permissionMenu);
            }

        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByPermissionId(Long permissionId) {

        permissionMenuDao.update("delete from op_adm_permission_menu  where permission_id = ? ",permissionId);
        return true;
    }
}
