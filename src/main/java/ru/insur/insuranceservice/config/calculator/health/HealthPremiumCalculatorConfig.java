package ru.insur.insuranceservice.config.calculator.health;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ms.insurance.calculator.health")
@Validated
public class HealthPremiumCalculatorConfig {
    @NotNull
    private BigDecimal baseRate;
    @Valid
    @NestedConfigurationProperty
    private AgeFactor ageFactor;
    @NotNull
    private BigDecimal  smokerFactor;
    @NotNull
    private BigDecimal  chronicDiseaseFactor;
}
