package com.elecredit.op.service;

import com.elecredit.SuperTest;
import com.elecredit.op.model.Menu;
import com.elecredit.service.IDGenerateService;
import com.elecredit.user.exception.UserNotExistException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MenuServiceImplTest extends SuperTest {
    @Autowired
    private IDGenerateService idGenerateService;
    @Autowired
    private MenuService menuService;
    @Test
    public void getById() {

        Menu menu = menuService.getById(2l);
    }

    @Test
    public void getByParent() {
    }

    @Test
    public void getAllByParent() {
    }

    @Test
    public void getAll() {


    }

    @Test
    public void getByUser() {

        List<Menu> listMenu = menuService.getByUser(453209098863771648l);

    }


    @Test
    public void save() {
        Menu menu = new Menu();
        menu.setMenuId(idGenerateService.getId());
        menu.setMenuName("元素征信");
        menu.setParentId(null);
        menu.setMenuIcon("css");
        menuService.save(menu);
        menuService.delete(menu);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
        menuService.deleteById(453516179126878208l);
    }
}