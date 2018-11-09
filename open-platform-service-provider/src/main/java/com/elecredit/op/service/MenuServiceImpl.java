package com.elecredit.op.service;

import com.elecredit.op.dao.MenuDao;
import com.elecredit.op.model.Menu;
import com.elecredit.op.model.Permission;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 菜单服务实现类
 */
@Component
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MenuService menuService;

    @Override
    public Menu getById(Long id) {
        return menuDao.getById(id);
    }

    @Override
    public Menu getByMenuName(String menuName) {
        return menuDao.findOne("select * from op_adm_menu where menu_name = ? order by sort_number,menu_id", new BeanPropertyRowMapper(Menu.class), menuName);
    }

    @Override
    public List<Menu> getAll() {
        return menuDao.findList("select  * from op_adm_menu order by sort_number,menu_id");
    }

    @Override
    public List<Menu> getByUser(Long userId) {

        List<Permission> listPermission = permissionService.getAllByEntity(userId);
        List<Long> permissions = new ArrayList<Long>();
        if(listPermission != null) {
            for (Permission permission : listPermission) {
                permissions.add(permission.getPermissionId());
            }
        }
        return getByPermissions(permissions);
    }

    @Override
    public List<Menu> getByPermissions(List<Long> permissions) {

        ArrayList placeholders = new ArrayList();
        ArrayList data = new ArrayList();
        if(permissions.size() == 0)
            return Collections.emptyList();
        else{
            for(Long permission : permissions){
                 placeholders.add("?");
                 data.add(permission);
            }
            return menuDao.findList("SELECT distinct b.* FROM op_adm_permission_menu a,op_adm_menu b where a.menu_id=b.menu_id and a.permission_id in ("+StringUtil.join(placeholders.toArray(),",") +") order by b.sort_number,b.menu_id",data.toArray());
        }
    }

    @Override
    public List<Menu> getChildrenByParent(Long id) {

        return menuDao.findList("select * from op_adm_menu where parent_id = ? order by sort_number,menu_id",id);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Menu save(Menu menu) {
        try {
            return menuDao.save(menu);
        }catch (DuplicateKeyException e){
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Menu update(Menu menu) {
        return menuDao.update(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(List<Menu> menuList) {

        for(Menu menu : menuList){
            if(menu.getMenuId() == null){
                return false;
            }
            menuDao.update(menu);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Menu menu) {

        List<Menu> childs = getChildrenByParent(menu.getMenuId());
        if(childs.size() == 0) {
            return menuDao.delete(menu);
        }
        else{
            for(Menu child : childs){
                delete(child);
            }

            return menuDao.delete(menu);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        return menuDao.deleteById(id);
    }

}
