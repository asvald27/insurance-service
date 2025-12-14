package ru.insur.insuranceservice.calculator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.insur.insuranceservice.config.calculator.health.HealthPremiumCalculatorConfig;
import ru.insur.insuranceservice.dto.CreateHealthPolicyRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j
@RequiredArgsConstructor
public class HealthPremiumCalculator {

    private final HealthPremiumCalculatorConfig config;

    public BigDecimal calculatePremium(CreateHealthPolicyRequest request) {
        BigDecimal premium = config.getBaseRate();

        BigDecimal ageFactor = getAgeFactor(request.insuredAge());
        premium = premium.multiply(ageFactor);

        if (Boolean.TRUE.equals(request.isSmoker())) {
            premium = premium.multiply(config.getSmokerFactor());
        }

        if (Boolean.TRUE.equals(request.hasChronicDiseases())) {
            premium = premium.multiply(config.getChronicDiseaseFactor());
        }

        return premium.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getAgeFactor(Integer age) {
        if (age <= 25) return config.getAgeFactor().getYoung();
        if (age <= 40) return config.getAgeFactor().getMiddle();
        return config.getAgeFactor().getSenior();
    }
}
