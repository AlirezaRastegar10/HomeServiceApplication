package com.example.exceptions.subservice;


import com.example.dto.exception.ResponseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class SubServiceExistExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SubServiceExistException.class)
    public ResponseEntity<ResponseException> handleSubServiceExistException(SubServiceExistException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }
}
