package com.example.exceptions.email;

public class EmailException extends RuntimeException {
    public EmailException(String message) {
        super(message);
    }
}
