package com.example.exceptions.service;

public class ServiceExistException extends RuntimeException {
    public ServiceExistException(String message) {
        super(message);
    }
}
