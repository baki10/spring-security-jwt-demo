package com.bakigoal.securitydemo.exception;

public class AccessCheckException extends RuntimeException {
    public AccessCheckException(String message) {
        super(message);
    }
}
