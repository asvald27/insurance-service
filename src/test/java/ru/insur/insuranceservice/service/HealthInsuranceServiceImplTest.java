package ru.insur.insuranceservice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.insur.insuranceservice.calculator.HealthPremiumCalculator;
import ru.insur.insuranceservice.db.entity.HealthInsurancePolicy;
import ru.insur.insuranceservice.db.repository.HealthInsurancePolicyRepository;
import ru.insur.insuranceservice.dto.CreateHealthPolicyRequest;
import ru.insur.insuranceservice.dto.HealthPolicyResponse;
import ru.insur.insuranceservice.mapper.HealthInsuranceMapper;
import ru.insur.insuranceservice.service.impl.HealthInsuranceServiceImpl;
import ru.insur.insuranceservice.validator.HealthPolicyValidator;
import testUtil.healthTestUtil.HealthTestUtil;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HealthInsuranceServiceImplTest {
    @Mock
    private HealthInsurancePolicyRepository healthInsurancePolicyRepository;
    @Mock
    private HealthPolicyValidator policyValidator;
    @Mock
    private HealthPremiumCalculator premiumCalculator;
    @Mock
    private HealthInsuranceMapper healthInsuranceMapper;

    @InjectMocks
    private HealthInsuranceServiceImpl healthInsuranceService;

    @Nested
    @DisplayName("createPolicy()")
    class createPolicyTest {

        @DisplayName("Создание полиса страхования здоровья")
        @Test
        void createPolicy() {
            CreateHealthPolicyRequest request = HealthTestUtil.createValidHealthPolicyRequest();
            BigDecimal premiumAmount = BigDecimal.valueOf(1.0);
            HealthInsurancePolicy policy = HealthTestUtil.createValidHealthInsurancePolicy();
            HealthPolicyResponse response = HealthTestUtil.createValidHealthPolicyResponse();
            when(premiumCalculator.calculatePremium(request)).thenReturn(premiumAmount);
            when(healthInsuranceMapper.toHealthInsurancePolicy(request, premiumAmount)).thenReturn(policy);
            when(healthInsurancePolicyRepository.save(policy)).thenReturn(policy);
            when(healthInsuranceMapper.toHealthPolicyResponse(policy)).thenReturn(response);

            var expected = HealthTestUtil.createValidHealthPolicyResponse();

            var result = healthInsuranceService.createPolicy(request);

            assertEquals(expected, result);
            verify(healthInsurancePolicyRepository).save(policy);
        }
    }
}
