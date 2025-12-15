package ru.insur.insuranceservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.insur.insuranceservice.calculator.LifePremiumCalculator;
import ru.insur.insuranceservice.db.entity.LifeInsurancePolicy;
import ru.insur.insuranceservice.db.repository.LifeInsurancePolicyRepository;
import ru.insur.insuranceservice.dto.CreateLifePolicyRequest;
import ru.insur.insuranceservice.dto.LifeExpiredPolicyResponse;
import ru.insur.insuranceservice.dto.LifePolicyByStatusResponse;
import ru.insur.insuranceservice.dto.LifePolicyResponse;
import ru.insur.insuranceservice.enums.PolicyStatus;
import ru.insur.insuranceservice.exception.LifePolicyNotFoundException;
import ru.insur.insuranceservice.mapper.LifeInsuranceMapper;
import ru.insur.insuranceservice.service.LifeInsuranceService;
import ru.insur.insuranceservice.validator.LifePolicyValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LifeInsuranceServiceImpl implements LifeInsuranceService {

    private final LifeInsurancePolicyRepository lifeInsurancePolicyRepository;
    private final LifePolicyValidator validator;
    private final LifeInsuranceMapper lifeInsuranceMapper;
    private final LifePremiumCalculator lifePremiumCalculator;

    @Override
    public LifePolicyResponse createPolicy(CreateLifePolicyRequest request) {
        validator.validate(request);

        BigDecimal premiumAmount = lifePremiumCalculator.calculatePremium(request);

        LifeInsurancePolicy policy = lifeInsuranceMapper.toLifeInsurancePolicy(request, premiumAmount);

        LifeInsurancePolicy savedPolicy = lifeInsurancePolicyRepository.save(policy);

        return lifeInsuranceMapper.toLifePolicyResponse(savedPolicy);
    }

    @Override
    public void activatePolicy(UUID policyId) {
        log.info("Activating health insurance policy: {}", policyId);

        LifeInsurancePolicy policy = lifeInsurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new LifePolicyNotFoundException("Health policy not found: " + policyId));

        validateActivatePolicy(policy);

        policy.setStatus(PolicyStatus.ACTIVE);
        policy.setUpdatedAt(LocalDateTime.now()); // @PreUpdate сработает

        lifeInsurancePolicyRepository.save(policy);
        log.info("Health insurance policy activated: {}", policyId);
    }

    @Override
    public LifePolicyResponse getPolicy(UUID policyId) {
        LifeInsurancePolicy healthInsurancePolicy = lifeInsurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new LifePolicyNotFoundException("Health policy not found: " + policyId));
        return lifeInsuranceMapper.toLifePolicyResponse(healthInsurancePolicy);
    }

    @Override
    public LifePolicyResponse getPolicyByClientId(UUID clientId) {
        LifeInsurancePolicy policy = lifeInsurancePolicyRepository.findByClientExternalId(clientId);
        if (policy == null) {
            throw new LifePolicyNotFoundException("Policy not found: " + clientId);
        }
        return lifeInsuranceMapper.toLifePolicyResponse(policy);
    }

    @Override
    public LifePolicyByStatusResponse getPoliciesByStatus(String status) {
        List<LifeInsurancePolicy> policies = lifeInsurancePolicyRepository.findByStatus(PolicyStatus.valueOf(status));
        if (policies == null) {
            throw new LifePolicyNotFoundException("Policy not found: " + status);
        }
        List<LifePolicyResponse> policiesByStatus = policies.stream()
                .map(lifeInsuranceMapper::toLifePolicyResponse).toList();
        return new LifePolicyByStatusResponse(policiesByStatus);
    }

    @Override
    public LifeExpiredPolicyResponse getExpiredPolicies(String date) {
        List<LifeInsurancePolicy> policies = lifeInsurancePolicyRepository.findExpiredPolicies(LocalDate.parse(date));
        if (policies == null) {
            log.info("Просроченные полисы не найдены.");
        }
        assert policies != null;
        List<LifePolicyResponse> expiredPolicyResponse = policies.stream()
                .map(lifeInsuranceMapper::toLifePolicyResponse).toList();
        return new LifeExpiredPolicyResponse(expiredPolicyResponse);
    }

    private void validateActivatePolicy(LifeInsurancePolicy policy) {
        if (policy.getStatus() != PolicyStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT policies can be activated");
        }

        if (policy.getStartDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Policy start date is in the future");
        }
    }

}
