package ru.insur.insuranceservice.exception;

public class LifePolicyNotFoundException extends RuntimeException {
    public LifePolicyNotFoundException(String message) {
        super(message);
    }
}
