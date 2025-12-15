package ru.insur.insuranceservice.dto;

import ru.insur.insuranceservice.enums.PolicyStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record LifePolicyResponse(
        String policyNumber,
        UUID clientExternalId,
        LocalDate startDate,
        LocalDate endDate,
        PolicyStatus status,
        BigDecimal premiumAmount,
        BigDecimal coverageAmount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer policyTermYears
) {
}
