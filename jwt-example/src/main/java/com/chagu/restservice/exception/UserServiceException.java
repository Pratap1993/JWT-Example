package com.chagu.restservice.exception;

public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = -8994453260053038669L;

    public UserServiceException(String exceptionMessage) {
        super(exceptionMessage);
    }

}
