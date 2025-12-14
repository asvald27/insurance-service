package ru.insur.insuranceservice.config.calculator.health;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ms.insurance.calculator.health")  // ← Должно быть точно так
@Validated  // ← Добавьте если используете валидацию
public class HealthPremiumCalculatorConfig {
    @NotNull
    private BigDecimal baseRate;
    @Valid  // ← Для вложенных объектов
    private AgeFactor ageFactor;
    @NotNull
    private BigDecimal  smokerFactor;
    @NotNull
    private BigDecimal  chronicDiseaseFactor;
}
