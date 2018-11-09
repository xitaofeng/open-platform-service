package com.elecredit.op.exception;

/**
 * 权限标识已存在
 */
public class PermissionFlagAlreadyExistException extends Exception{
    public PermissionFlagAlreadyExistException(){
        super("权限标识已存在");
    }
}
