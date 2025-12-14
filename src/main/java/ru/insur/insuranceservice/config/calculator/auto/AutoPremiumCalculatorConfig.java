package ru.insur.insuranceservice.config.calculator.auto;

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
@ConfigurationProperties(prefix = "ms.insurance.calculator.auto")  // ← Должно быть точно так
@Validated  // ← Добавьте если используете валидацию
public class AutoPremiumCalculatorConfig {

    @NotNull
    private BigDecimal baseRate;
    @NotNull
    private BigDecimal  driverExperienceFactor;
    @NotNull
    private BigDecimal  accidentFactor;
}
