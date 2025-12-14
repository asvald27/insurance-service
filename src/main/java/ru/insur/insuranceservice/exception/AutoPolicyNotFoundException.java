package ru.insur.insuranceservice.exception;

public class AutoPolicyNotFoundException extends RuntimeException {
    public AutoPolicyNotFoundException(String message) {
        super(message);
    }
}
