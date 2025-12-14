package ru.insur.insuranceservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.insur.insuranceservice.calculator.AutoPremiumCalculator;
import ru.insur.insuranceservice.db.entity.AutoInsurancePolicy;
import ru.insur.insuranceservice.db.repository.AutoInsurancePolicyRepository;
import ru.insur.insuranceservice.dto.AutoExpiredPolicyResponse;
import ru.insur.insuranceservice.dto.AutoPolicyByStatusResponse;
import ru.insur.insuranceservice.dto.AutoPolicyResponse;
import ru.insur.insuranceservice.dto.CreateAutoPolicyRequest;
import ru.insur.insuranceservice.enums.PolicyStatus;
import ru.insur.insuranceservice.exception.AutoPolicyNotFoundException;
import ru.insur.insuranceservice.mapper.AutoInsuranceMapper;
import ru.insur.insuranceservice.service.AutoInsuranceService;
import ru.insur.insuranceservice.validator.AutoPolicyValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoInsuranceServiceImpl implements AutoInsuranceService {

    private final AutoInsurancePolicyRepository autoInsurancePolicyRepository;
    private final AutoPremiumCalculator premiumCalculator;
    private final AutoPolicyValidator autoPolicyValidator;
    private final AutoInsuranceMapper autoInsuranceMapper;

    @Override
    public AutoPolicyResponse createPolicy(CreateAutoPolicyRequest request) {
        autoPolicyValidator.validate(request);

        BigDecimal premiumAmount = premiumCalculator.calculatePremium(request);

        AutoInsurancePolicy policy = autoInsuranceMapper.toAutoInsurancePolicy(request, premiumAmount);

        AutoInsurancePolicy savedPolicy = autoInsurancePolicyRepository.save(policy);

        return autoInsuranceMapper.toAutoPolicyResponse(savedPolicy);
    }

    @Override
    public void activatePolicy(UUID policyId) {
        log.info("Activating auto insurance policy: {}", policyId);

        AutoInsurancePolicy policy = autoInsurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new AutoPolicyNotFoundException("Auto insurance policy not found" + policyId));

        validateActivatePolicy(policy);

        policy.setStatus(PolicyStatus.ACTIVE);
        policy.setUpdatedAt(LocalDateTime.now());

        autoInsurancePolicyRepository.save(policy);
        log.info("Activated auto insurance policy: {}", policyId);
    }

    @Override
    public AutoPolicyResponse getPolicy(UUID policyId) {
        AutoInsurancePolicy autoInsurancePolicy = autoInsurancePolicyRepository.findById(policyId)
                .orElseThrow(() -> new AutoPolicyNotFoundException("Auto insurance policy not found" + policyId));
        return autoInsuranceMapper.toAutoPolicyResponse(autoInsurancePolicy);
    }

    @Override
    public AutoPolicyResponse getPolicyByClientId(UUID clientId) {
        AutoInsurancePolicy policy = autoInsurancePolicyRepository.findByClientExternalId(clientId);
        if (policy == null) {
            throw new AutoPolicyNotFoundException("Auto insurance policy not found" + clientId);
        }
        return autoInsuranceMapper.toAutoPolicyResponse(policy);
    }

    @Override
    public AutoPolicyByStatusResponse getPoliciesByStatus(String status) {
        List<AutoInsurancePolicy> policies = autoInsurancePolicyRepository.findByStatus(PolicyStatus.valueOf(status));
        if (policies == null) {
            throw new AutoPolicyNotFoundException("Policy not found: " + status);
        }
        List<AutoPolicyResponse> policiesByStatus = policies.stream()
                .map(autoInsuranceMapper::toAutoPolicyResponse).toList();
        return new AutoPolicyByStatusResponse(policiesByStatus);
    }

    @Override
    public AutoExpiredPolicyResponse getExpiredPolicies(String date) {
        List<AutoInsurancePolicy> policies = autoInsurancePolicyRepository.findExpiredPolicies(LocalDate.parse(date));
        if (policies == null) {
            log.info("Просроченные полисы не найдены.");
        }
        assert policies != null;
        List<AutoPolicyResponse> expiredPolicyResponse = policies.stream()
                .map(autoInsuranceMapper::toAutoPolicyResponse).toList();
        return new AutoExpiredPolicyResponse(expiredPolicyResponse);
    }

    private void validateActivatePolicy(AutoInsurancePolicy policy) {
        if (policy.getStatus() != PolicyStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT policies can be activated");
        }

        if (policy.getStartDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Policy start date is in the future");
        }
    }
}
