package ru.insur.insuranceservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateLifePolicyRequest(
        String policyNumber,
        UUID clientExternalId,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal coverageAmount,
        Integer insuredAge,
        String beneficiaryName,
        String beneficiaryRelation,
        boolean isHighRiskOccupation,
        boolean hasDangerousHobbies,
        int policyTermYears
) {

    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return !today.isBefore(startDate) && !today.isAfter(endDate);
    }
}
