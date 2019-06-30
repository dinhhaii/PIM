package com.pim.exception;

public class ProjectNotExistsException extends Exception {
    public ProjectNotExistsException(String errorMessage){
        super(errorMessage);
    }
    public ProjectNotExistsException(String errorMessage, Throwable error){
        super(errorMessage, error);
    }
}
