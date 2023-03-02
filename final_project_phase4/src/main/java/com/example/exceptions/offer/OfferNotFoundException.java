package com.example.exceptions.offer;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(String message) {
        super(message);
    }
}
