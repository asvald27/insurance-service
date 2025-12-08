package ru.insur.insuranceservice.dto;

import ru.insur.insuranceservice.enums.PolicyStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record HealthPolicyResponse(
        UUID id,
        String policyNumber,
        UUID clientExternalId,
        LocalDate startDate,
        LocalDate endDate,
        PolicyStatus status,
        BigDecimal premiumAmount,
        BigDecimal coverageAmount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer insuredAge,
        Boolean hasChronicDiseases,
        Boolean isSmoker,
        String coverageType,
        Boolean dentalCoverage,
        Boolean hospitalizationCoverage,
        Integer medicalCheckupFrequency
) {
    // Вспомогательные методы
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

    public Optional<BigDecimal> getDailyPremium() {
        if (premiumAmount == null || startDate == null || endDate == null) {
            return Optional.empty();
        }
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        if (days <= 0) return Optional.empty();

        return Optional.of(premiumAmount.divide(BigDecimal.valueOf(days), 2, java.math.RoundingMode.HALF_UP));
    }
}
