package com.example.exceptions.user;

public class LessCreditException extends RuntimeException {
    public LessCreditException(String message) {
        super(message);
    }
}
