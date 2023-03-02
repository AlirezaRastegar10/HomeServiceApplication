package com.example.exceptions.user;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
