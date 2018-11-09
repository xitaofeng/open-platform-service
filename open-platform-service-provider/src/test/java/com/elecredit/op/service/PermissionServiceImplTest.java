package com.elecredit.op.service;

import com.elecredit.SuperTest;
import com.elecredit.op.exception.PermissionFlagAlreadyExistException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PermissionServiceImplTest extends SuperTest {
    @Autowired
    PermissionService permissionService;

    @Test
    public void save() throws PermissionFlagAlreadyExistException {
//        Permission permission = new Permission();
//        permission.setFlag("user.save");
//        permission.setPermissionName("新增用户");
//        permission.setPermissionType(PermissionType.SYSTEM);
//        permissionService.save(permission);

    }

}