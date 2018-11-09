package com.elecredit.op.dao;

import com.elecredit.SuperTest;
import com.elecredit.service.IDGenerateService;
import com.elecredit.op.model.Supplier;
import com.elecredit.user.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SupplierDaoTest extends SuperTest {
    @Autowired
    SupplierDao supplierDao;
    @Autowired
    IDGenerateService idGenerateService;
    @Test
    public void save(){
        long timeSpend = 0;
        for(int i = 1;i<10;i++) {
            long id = idGenerateService.getId();
            Supplier supplier = new Supplier();
            supplier.setSupplierId(id);
            supplier.setSupplierName("测试供应商");
            supplier.setContact("联系人");
            supplier.setTelephone("13800138000");
            supplier.setEmail("abcde@email.com");
            long time = System.currentTimeMillis();
            supplierDao.save(supplier);
            supplierDao.delete(supplier);
            timeSpend += (System.currentTimeMillis() - time);
        }
        System.out.println("插入用时：" + timeSpend);
    }

}