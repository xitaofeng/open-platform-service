package com.elecredit.op.exception;

public class ServiceApiIdAlreadyExistException extends Exception{
    public ServiceApiIdAlreadyExistException(){
        super("服务接口ID已存在");
    }
}
