package com.example.exceptions.user;

public class UserInActiveException extends RuntimeException {
    public UserInActiveException(String message) {
        super(message);
    }
}
