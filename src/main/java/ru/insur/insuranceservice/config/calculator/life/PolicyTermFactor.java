package ru.insur.insuranceservice.config.calculator.life;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PolicyTermFactor {
    @NotNull
    private BigDecimal shortTerm; // до 5 лет
    @NotNull
    private BigDecimal mediumTerm; // 6-15 лет
    @NotNull
    private BigDecimal longTerm; // 16+ лет
}
