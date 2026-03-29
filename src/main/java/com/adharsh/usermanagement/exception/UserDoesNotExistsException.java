package com.adharsh.usermanagement.exception;

public class UserDoesNotExistsException extends RuntimeException {
    public UserDoesNotExistsException(String msg) {
        super(msg);
    }
}
