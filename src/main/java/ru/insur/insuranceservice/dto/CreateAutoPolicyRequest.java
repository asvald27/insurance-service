package ru.insur.insuranceservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateAutoPolicyRequest(
        @NotNull UUID clientExternalId,

        @NotNull @FutureOrPresent
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate,

        @NotNull @Future
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate,

        @NotNull @Positive BigDecimal coverageAmount,

        @NotNull @Min(18) @Max(80) Integer driverAge,

        @NotNull
        String vehicleVin,

        @NotNull
        String vehicleModel,

        @NotNull
        String vehicleYear,

        @NotNull
        Integer driverExperienceYears,

        @NotNull
        Boolean hasAccidentHistory,

        @NotNull
        Integer enginePowerHp,

        @NotNull
        String driverLicenseCategory
) {
}
