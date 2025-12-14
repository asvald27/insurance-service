package ru.insur.insuranceservice.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.insur.insuranceservice.dto.CreateAutoPolicyRequest;
import ru.insur.insuranceservice.exception.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Валидация входящего запроса для создания полиса
 */
@Component
@Slf4j
public class AutoPolicyValidator implements Validator<CreateAutoPolicyRequest> {

    @Override
    public void validate(CreateAutoPolicyRequest request) {
        List<String> errors = new ArrayList<>();

        if (request.startDate().isAfter(request.endDate())) {
            errors.add("Start date must be before end date");
        }

        if (request.startDate().isBefore(LocalDate.now())) {
            errors.add("Start date cannot be in the past");
        }

        if (request.coverageAmount().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Coverage amount must be positive");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Policy validation failed: " + String.join(", ", errors));
        }
    }
}
