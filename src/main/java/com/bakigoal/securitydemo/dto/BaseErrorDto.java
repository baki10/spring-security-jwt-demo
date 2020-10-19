package com.bakigoal.securitydemo.dto;

import lombok.Data;

@Data
public class BaseErrorDto {
    String exception;
    String message;

    public BaseErrorDto(Exception e) {
        this.exception = e.getClass().getSimpleName();
        this.message = e.getMessage();
    }
}
