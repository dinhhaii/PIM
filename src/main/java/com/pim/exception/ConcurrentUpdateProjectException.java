package com.pim.exception;

public class ConcurrentUpdateProjectException extends Exception {
    public ConcurrentUpdateProjectException(String messageError){
        super(messageError);
    }
    public ConcurrentUpdateProjectException(String messageError, Throwable err){
        super(messageError,err);
    }
}
