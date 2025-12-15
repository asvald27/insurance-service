package ru.insur.insuranceservice.config.calculator.life;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GenderFactor {
    @NotNull
    private BigDecimal male;
    @NotNull
    private BigDecimal female;
}
