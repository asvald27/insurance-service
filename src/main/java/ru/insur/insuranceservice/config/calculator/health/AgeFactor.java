package ru.insur.insuranceservice.config.calculator.health;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AgeFactor {
    @NotNull
    private BigDecimal young;
    @NotNull
    private BigDecimal middle;
    @NotNull
    private BigDecimal senior;
    @NotNull
    private BigDecimal elderly;
}
