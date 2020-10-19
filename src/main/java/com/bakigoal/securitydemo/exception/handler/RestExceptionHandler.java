package com.bakigoal.securitydemo.exception.handler;

import com.bakigoal.securitydemo.dto.BaseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new BaseErrorDto(e));
    }
}
