package com.example.exceptions.subservice;

public class SubServiceNotFoundException extends RuntimeException {
    public SubServiceNotFoundException(String message) {
        super(message);
    }
}
