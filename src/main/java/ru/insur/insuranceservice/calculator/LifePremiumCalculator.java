package ru.insur.insuranceservice.calculator;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.insur.insuranceservice.config.calculator.life.LifePremiumCalculatorConfig;
import ru.insur.insuranceservice.dto.CreateLifePolicyRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j
@RequiredArgsConstructor
public class LifePremiumCalculator {

    private final LifePremiumCalculatorConfig config;

    public BigDecimal calculatePremium(CreateLifePolicyRequest request) {
        log.info("Calculating premium for life policy: {}", request.policyNumber());

        // Базовый расчет: сумма покрытия * базовая ставка / 1000
        BigDecimal basePremium = request.coverageAmount()
                .multiply(config.getBaseRatePerThousand())
                .divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);

        BigDecimal premium = basePremium;

        // Возрастной коэффициент
        BigDecimal ageFactor = getAgeFactor(request.insuredAge());
        premium = premium.multiply(ageFactor);

        // Коэффициент срока полиса
        BigDecimal termFactor = getTermFactor(request.policyTermYears());
        premium = premium.multiply(termFactor);

        // Коэффициент рискованной профессии
        if (request.isHighRiskOccupation()) {
            premium = premium.multiply(config.getHighRiskOccupationFactor());
        }

        // Коэффициент опасных хобби
        if (request.hasDangerousHobbies()) {
            premium = premium.multiply(config.getDangerousHobbiesFactor());
        }

        // Коэффициент родства с бенефициаром (если не близкий родственник)
        if (!isCloseRelative(request.beneficiaryRelation())) {
            premium = premium.multiply(config.getBeneficiaryRelationFactor());
        }

        return premium.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getAgeFactor(Integer age) {
        if (age <= 30) {
            return config.getAgeFactor().getYoung();
        } else if (age <= 50) {
            return config.getAgeFactor().getMiddle();
        } else if (age <= 65) {
            return config.getAgeFactor().getSenior();
        } else {
            return config.getAgeFactor().getElderly();
        }
    }

    private BigDecimal getTermFactor(Integer termYears) {
        if (termYears <= 5) {
            return config.getPolicyTermFactor().getShortTerm();
        } else if (termYears <= 15) {
            return config.getPolicyTermFactor().getMediumTerm();
        } else {
            return config.getPolicyTermFactor().getLongTerm();
        }
    }

    private boolean isCloseRelative(String relation) {
        if (relation == null) return false;

        String normalizedRelation = relation.toLowerCase().trim();
        return normalizedRelation.matches("(?i).*(супруг|жена|муж|родитель|ребенок|сын|дочь|мать|отец).*");
    }
}
