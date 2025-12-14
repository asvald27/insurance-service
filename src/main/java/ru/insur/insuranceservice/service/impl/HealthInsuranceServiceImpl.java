package ru.insur.insuranceservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.insur.insuranceservice.calculator.HealthPremiumCalculator;
import ru.insur.insuranceservice.db.entity.HealthInsurancePolicy;
import ru.insur.insuranceservice.db.repository.HealthInsurancePolicyRepository;
import ru.insur.insuranceservice.dto.CreateHealthPolicyRequest;
import ru.insur.insuranceservice.dto.HealthExpiredPolicyResponse;
import ru.insur.insuranceservice.dto.HealthPoliceByStatusResponse;
import ru.insur.insuranceservice.dto.HealthPolicyResponse;
import ru.insur.insuranceservice.enums.PolicyStatus;
import ru.insur.insuranceservice.exception.HealthPolicyNotFoundException;
import ru.insur.insuranceservice.mapper.HealthInsuranceMapper;
import ru.insur.insuranceservice.service.HealthInsuranceService;
import ru.insur.insuranceservice.validator.HealthPolicyValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class HealthInsuranceServiceImpl implements HealthInsuranceService {

    private final HealthInsurancePolicyRepository healthInsurancePolicyRepository;
    private final HealthPolicyValidator policyValidator;
    private final HealthPremiumCalculator premiumCalculator;
    private final HealthInsuranceMapper healthInsuranceMapper;

    @Override
    public HealthPolicyResponse createPolicy(CreateHealthPolicyRequest request) {
        policyValidator.validate(request);
        log.info("Creating health insurance policy for client: {}", request.clientExternalId());

        BigDecimal premiumAmount = premiumCalculator.calculatePremium(request);

        HealthInsurancePolicy policy = healthInsuranceMapper.toHealthInsurancePolicy(request, premiumAmount);

        HealthInsurancePolicy savedPolicy = healthInsurancePolicyRepository.save(policy);

        return healthInsuranceMapper.toHealthPolicyResponse(savedPolicy);
    }

    @Override
    public void activatePolicy(UUID policyId) {
        log.info("Activating health insurance policy: {}", policyId);

        HealthInsurancePolicy policy = healthInsurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new HealthPolicyNotFoundException("Health policy not found: " + policyId));

        validateActivatePolicy(policy);

        policy.setStatus(PolicyStatus.ACTIVE);
        policy.setUpdatedAt(LocalDateTime.now()); // @PreUpdate сработает

        healthInsurancePolicyRepository.save(policy);
        log.info("Health insurance policy activated: {}", policyId);
    }

    @Override
    public HealthPolicyResponse getPolicy(UUID policyId) {
        HealthInsurancePolicy healthInsurancePolicy = healthInsurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new HealthPolicyNotFoundException("Health policy not found: " + policyId));
        return healthInsuranceMapper.toHealthPolicyResponse(healthInsurancePolicy);
    }

    @Override
    public HealthPolicyResponse getPolicyByClientId(UUID clientId) {
        HealthInsurancePolicy policy = healthInsurancePolicyRepository.findByClientExternalId(clientId);
        if (policy == null) {
            throw new HealthPolicyNotFoundException("Policy not found: " + clientId);
        }
        return healthInsuranceMapper.toHealthPolicyResponse(policy);
    }

    @Override
    public HealthPoliceByStatusResponse getPoliciesByStatus(String status) {
        List<HealthInsurancePolicy> policies = healthInsurancePolicyRepository.findByStatus(PolicyStatus.valueOf(status));
        if (policies == null) {
            throw new HealthPolicyNotFoundException("Policy not found: " + status);
        }
        List<HealthPolicyResponse> policiesByStatus = policies.stream()
                .map(healthInsuranceMapper::toHealthPolicyResponse).toList();
        return new HealthPoliceByStatusResponse(policiesByStatus);
    }

    @Override
    public HealthExpiredPolicyResponse getExpiredPolicies(String date) {
        List<HealthInsurancePolicy> policies = healthInsurancePolicyRepository.findExpiredPolicies(LocalDate.parse(date));
        if (policies == null) {
            log.info("Просроченные полисы не найдены.");
        }
        assert policies != null;
        List<HealthPolicyResponse> expiredPolicyResponse = policies.stream()
                .map(healthInsuranceMapper::toHealthPolicyResponse).toList();
        return new HealthExpiredPolicyResponse(expiredPolicyResponse);
    }

    private void validateActivatePolicy(HealthInsurancePolicy policy) {
        if (policy.getStatus() != PolicyStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT policies can be activated");
        }

        if (policy.getStartDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Policy start date is in the future");
        }
    }

}
