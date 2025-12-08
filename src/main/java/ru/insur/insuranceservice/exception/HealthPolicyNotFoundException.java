package ru.insur.insuranceservice.exception;

public class HealthPolicyNotFoundException extends RuntimeException {
    public HealthPolicyNotFoundException(String message) {
        super(message);
    }
}
