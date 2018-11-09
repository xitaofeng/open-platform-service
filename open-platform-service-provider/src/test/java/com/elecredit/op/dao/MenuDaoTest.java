package com.elecredit.op.dao;

import com.elecredit.SuperTest;
import com.elecredit.service.IDGenerateService;
import com.elecredit.op.model.Menu;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MenuDaoTest extends SuperTest {
    @Autowired
    MenuDao menuDao;
    @Autowired
    IDGenerateService idGenerateService;
    @Test
    public void save(){
        long timeSpend = 0;

        for(int i=0; i<10; i++) {
            long id = idGenerateService.getId();
            Menu menu = new Menu();
            menu.setMenuId(id);
            menu.setMenuName("菜单");
            menu.setParentId(0l);
            menu.setMenuIcon("css");
            long time = System.currentTimeMillis();
            menuDao.save(menu);
            menuDao.update(menu);
            menuDao.deleteById(id);
            timeSpend += (System.currentTimeMillis() - time);
        }
        System.out.println("插入用时：" + timeSpend);
    }
}