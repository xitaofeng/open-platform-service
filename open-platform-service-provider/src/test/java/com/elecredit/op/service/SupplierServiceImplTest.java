package com.elecredit.op.service;

import com.elecredit.SuperTest;
import com.elecredit.service.IDGenerateService;
import com.elecredit.op.model.Supplier;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SupplierServiceImplTest extends SuperTest {
    @Autowired
    private IDGenerateService idGenerateService;
    @Autowired
    private SupplierService supplierService;
    @Test
    public void getById() {

        Supplier supplier = supplierService.getById(456871185469145088l);
        System.out.println(supplier);

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
    public void getByName() {

        Supplier listSupplier = supplierService.getByName("测试供应商");

    }


    @Test
    public void save(){
        long id = idGenerateService.getId();
        Supplier supplier = new Supplier();
        supplier.setSupplierId(id);
        supplier.setSupplierName("测试供应商");
        supplier.setContact("联系人");
        supplier.setTelephone("13800138000");
        supplier.setEmail("abcde@email.com");
        supplierService.save(supplier);
        supplierService.delete(supplier);


    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {

    }
}