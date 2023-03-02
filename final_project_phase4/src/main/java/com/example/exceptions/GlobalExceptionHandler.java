package com.example.exceptions;


import com.example.dto.exception.ResponseException;
import com.example.exceptions.file.BigFileException;
import com.example.exceptions.file.FileNotFoundException;
import com.example.exceptions.file.FileNotJPGException;
import com.example.exceptions.offer.OfferNotFoundException;
import com.example.exceptions.order.LessProposedPriceException;
import com.example.exceptions.order.OrderNotFoundException;
import com.example.exceptions.time.LessTimeException;
import com.example.exceptions.user.PasswordNotMatchException;
import com.example.exceptions.user.UserInActiveException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ResponseException> illegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ResponseException> nullPointerException(NullPointerException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    public ResponseEntity<ResponseException> fileNotFoundException(FileNotFoundException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = BigFileException.class)
    public ResponseEntity<ResponseException> bigFileException(BigFileException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = FileNotJPGException.class)
    public ResponseEntity<ResponseException> fileNotJPGException(FileNotJPGException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<ResponseException> iOException(IOException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = PasswordNotMatchException.class)
    public ResponseEntity<ResponseException> passwordNotMatchException(PasswordNotMatchException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = LessTimeException.class)
    public ResponseEntity<ResponseException> lessTimeException(LessTimeException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = DateTimeParseException.class)
        public ResponseEntity<ResponseException> dateTimeParseException(HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                "the format of the entered date should be as follows -> year-month-day hour:minute.",
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = LessProposedPriceException.class)
    public ResponseEntity<ResponseException> lessProposedPriceException(LessProposedPriceException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<ResponseException> orderNotFoundException(OrderNotFoundException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = OfferNotFoundException.class)
    public ResponseEntity<ResponseException> offerNotFoundException(OfferNotFoundException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = UserInActiveException.class)
    public ResponseEntity<ResponseException> userInActiveException(UserInActiveException exception, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ResponseException> usernameNotFoundException(HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                "you are unauthorized.",
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }
}
