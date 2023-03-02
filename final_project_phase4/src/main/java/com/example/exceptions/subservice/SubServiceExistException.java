package com.example.exceptions.subservice;

public class SubServiceExistException extends RuntimeException {
    public SubServiceExistException(String message) {
        super(message);
    }
}
