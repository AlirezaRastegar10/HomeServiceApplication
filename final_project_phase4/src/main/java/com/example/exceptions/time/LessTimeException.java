package com.example.exceptions.time;

public class LessTimeException extends RuntimeException {
    public LessTimeException(String message) {
        super(message);
    }
}
