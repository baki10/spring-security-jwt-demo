package com.bakigoal.securitydemo.exception.handler;

import com.bakigoal.securitydemo.dto.BaseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception e) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof AuthenticationException) {
            status = HttpStatus.FORBIDDEN;
        }
        return ResponseEntity.status(status).body(new BaseErrorDto(e));
    }
}
