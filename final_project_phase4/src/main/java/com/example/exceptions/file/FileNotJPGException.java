package com.example.exceptions.file;

public class FileNotJPGException extends RuntimeException {
    public FileNotJPGException(String message) {
        super(message);
    }
}
