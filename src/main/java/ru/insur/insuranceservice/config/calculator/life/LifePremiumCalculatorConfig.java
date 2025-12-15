package ru.insur.insuranceservice.config.calculator.life;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.insur.insuranceservice.config.calculator.health.AgeFactor;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ms.insurance.calculator.life")
@Validated
public class LifePremiumCalculatorConfig {
    @NotNull
    private BigDecimal baseRatePerThousand; // Базовая ставка на 1000 единиц покрытия
    @Valid
    @NestedConfigurationProperty
    private AgeFactor ageFactor;
    @Valid
    @NestedConfigurationProperty
    private PolicyTermFactor policyTermFactor;
    @NotNull
    private BigDecimal highRiskOccupationFactor;
    @NotNull
    private BigDecimal dangerousHobbiesFactor;
    @Valid
    private Map<String, BigDecimal> paymentFrequencyFactors;
    @Valid
    @NestedConfigurationProperty
    private GenderFactor genderFactor;
    @NotNull
    private BigDecimal beneficiaryRelationFactor;
}
