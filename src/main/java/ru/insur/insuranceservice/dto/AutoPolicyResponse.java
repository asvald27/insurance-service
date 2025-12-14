package ru.insur.insuranceservice.dto;

import ru.insur.insuranceservice.enums.PolicyStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AutoPolicyResponse(
        UUID id,
        String policyNumber,
        UUID clientExternalId,
        LocalDate startDate,
        LocalDate endDate,
        PolicyStatus status,
        BigDecimal premiumAmount,
        BigDecimal coverageAmount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public boolean isActive() {
        return status == PolicyStatus.ACTIVE;
    }

    public boolean isExpired() {
        return endDate.isBefore(LocalDate.now());
    }

    public boolean requiresRenewal() {
        return isActive() &&
                endDate.isBefore(LocalDate.now().plusMonths(1)) &&
                endDate.isAfter(LocalDate.now());
    }
}
