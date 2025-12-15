package ru.insur.insuranceservice.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.insur.insuranceservice.dto.CreateLifePolicyRequest;
import ru.insur.insuranceservice.exception.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class LifePolicyValidator {

    public void validate(CreateLifePolicyRequest request) {
        List<String> errors = new ArrayList<>();

        if (request.clientExternalId() == null) {
            errors.add("clientExternalId is required");
        }
        // Проверка дат
        if (request.startDate().isAfter(request.endDate())) {
            errors.add("Start date must be before end date");
        }

        if (request.startDate().isBefore(LocalDate.now())) {
            errors.add("Start date cannot be in the past");
        }

        // Проверка возраста
        if (request.insuredAge() < 18 || request.insuredAge() > 100) {
            errors.add("Insured age must be between 18 and 100");
        }

        // Проверка суммы покрытия
        if (request.coverageAmount().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Coverage amount must be positive");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Policy validation failed: " + String.join(", ", errors));
        }
    }
}
