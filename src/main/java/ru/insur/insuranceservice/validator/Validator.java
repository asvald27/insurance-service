package ru.insur.insuranceservice.validator;

public interface Validator<T> {
    void validate(T request);
}
