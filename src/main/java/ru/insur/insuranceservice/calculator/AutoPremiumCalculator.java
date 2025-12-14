package ru.insur.insuranceservice.calculator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.insur.insuranceservice.config.calculator.auto.AutoPremiumCalculatorConfig;
import ru.insur.insuranceservice.dto.CreateAutoPolicyRequest;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class AutoPremiumCalculator {

    private final AutoPremiumCalculatorConfig config;

    public BigDecimal calculatePremium(CreateAutoPolicyRequest request) {
        BigDecimal premium = config.getBaseRate();

        BigDecimal experienceRate = getExperienceRate(request);
        premium = premium.add(experienceRate);

        BigDecimal accidentFactor = getAccidentFactor(request);
        premium = premium.add(accidentFactor);
        return premium;
    }

    private BigDecimal getExperienceRate(CreateAutoPolicyRequest request) {
        if (request.driverExperienceYears() > 0 && request.driverExperienceYears() < 3) {
            return config.getDriverExperienceFactor().multiply(BigDecimal.valueOf(1.3));
        }
        if (request.driverExperienceYears() > 3 && request.driverExperienceYears() < 6) {
            return config.getDriverExperienceFactor().multiply(BigDecimal.valueOf(2.0));
        }
        if (request.driverExperienceYears() > 6 && request.driverExperienceYears() < 10) {
            return config.getDriverExperienceFactor().multiply(BigDecimal.valueOf(2.4));
        }
        if (request.driverExperienceYears() > 10) {
            return config.getDriverExperienceFactor().multiply(BigDecimal.valueOf(2.6));
        }
        else return config.getDriverExperienceFactor().multiply(BigDecimal.valueOf(3.0));
    }

    private BigDecimal getAccidentFactor(CreateAutoPolicyRequest request) {
        if (request.hasAccidentHistory().equals(Boolean.TRUE)) {
            return config.getAccidentFactor().subtract(BigDecimal.valueOf(2.0));
        }
        else return config.getAccidentFactor().multiply(BigDecimal.valueOf(1.3));
    }
}
