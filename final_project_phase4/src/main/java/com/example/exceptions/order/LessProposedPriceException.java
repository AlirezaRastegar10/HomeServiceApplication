package com.example.exceptions.order;

public class LessProposedPriceException extends RuntimeException {
    public LessProposedPriceException(String message) {
        super(message);
    }
}
