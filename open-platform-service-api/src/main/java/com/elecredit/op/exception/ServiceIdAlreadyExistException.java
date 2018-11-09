package com.elecredit.op.exception;

public class ServiceIdAlreadyExistException extends Exception{
    public ServiceIdAlreadyExistException(){
        super("服务ID已存在");
    }
}
