package ru.insur.insuranceservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record CreateHealthPolicyRequest(
        @NotNull UUID clientExternalId,

        @NotNull @FutureOrPresent
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate,

        @NotNull @Future
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate,

        @NotNull @Positive BigDecimal coverageAmount,

        @NotNull @Min(18) @Max(100) Integer insuredAge,

        Boolean hasChronicDiseases,

        Boolean isSmoker,

        @Pattern(regexp = "BASIC|EXTENDED|FAMILY")
        String coverageType,

        Boolean dentalCoverage,

        Boolean hospitalizationCoverage,

        @Min(0) @Max(12) Integer medicalCheckupFrequency
) {
        public CreateHealthPolicyRequest {
                // Валидация дат
                if (startDate.isAfter(endDate)) {
                        throw new IllegalArgumentException("Дата начала должна быть раньше даты окончания");
                }

                // Установка значений по умолчанию
                if (hasChronicDiseases == null) hasChronicDiseases = false;
                if (isSmoker == null) isSmoker = false;
                if (coverageType == null) coverageType = "BASIC";
                if (dentalCoverage == null) dentalCoverage = false;
                if (hospitalizationCoverage == null) hospitalizationCoverage = false;
                if (medicalCheckupFrequency == null) medicalCheckupFrequency = 0;
        }

        // Вспомогательные методы
        public Boolean isSmoker() {
                return Boolean.TRUE.equals(isSmoker);
        }

        public Boolean hasChronicDiseases() {
                return Boolean.TRUE.equals(hasChronicDiseases);
        }

        public boolean hasDentalCoverage() {
                return Boolean.TRUE.equals(dentalCoverage);
        }

        public boolean hasHospitalizationCoverage() {
                return Boolean.TRUE.equals(hospitalizationCoverage);
        }

        public Optional<Integer> getMedicalCheckupFrequency() {
                return Optional.ofNullable(medicalCheckupFrequency)
                        .filter(freq -> freq > 0);
        }
}