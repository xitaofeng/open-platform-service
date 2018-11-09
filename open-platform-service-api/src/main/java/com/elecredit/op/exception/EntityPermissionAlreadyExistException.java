package com.elecredit.op.exception;

/**
 * 用户权限已存在
 */
public class EntityPermissionAlreadyExistException extends Exception{
    public EntityPermissionAlreadyExistException(){
        super("用户权限已存在");
    }
}
