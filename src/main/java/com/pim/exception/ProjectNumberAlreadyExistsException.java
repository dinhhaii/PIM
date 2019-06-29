package com.pim.exception;

public class ProjectNumberAlreadyExistsException extends Exception {
    public ProjectNumberAlreadyExistsException(String errorMessage){
        super(errorMessage);
    }
    public ProjectNumberAlreadyExistsException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
