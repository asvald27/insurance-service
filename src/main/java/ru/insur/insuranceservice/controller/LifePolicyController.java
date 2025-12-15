package ru.insur.insuranceservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.insur.insuranceservice.dto.*;
import ru.insur.insuranceservice.service.LifeInsuranceService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/life-policies")
@RequiredArgsConstructor
@Validated
public class LifePolicyController {

    private final LifeInsuranceService lifeInsuranceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LifePolicyResponse createPolicy(@Valid @RequestBody CreateLifePolicyRequest request) {
        return lifeInsuranceService.createPolicy(request);
    }

    @PatchMapping("/{policyId}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activatePolicy(@PathVariable UUID policyId) {
        lifeInsuranceService.activatePolicy(policyId);
    }

    @GetMapping("/{policyId}")
    public LifePolicyResponse getPolicy(@PathVariable UUID policyId) {
        return lifeInsuranceService.getPolicy(policyId);
    }

    @GetMapping("/{clientId}")
    public LifePolicyResponse getPolicyByClientId(@PathVariable UUID clientId) {
        return lifeInsuranceService.getPolicyByClientId(clientId);
    }

    @GetMapping("/{status}")
    public LifePolicyByStatusResponse getPolicyByStatus(@PathVariable String status) {
        return lifeInsuranceService.getPoliciesByStatus(status);
    }

    @GetMapping("/{date}")
    public LifeExpiredPolicyResponse getPolicyByDate(@PathVariable String date) {
        return lifeInsuranceService.getExpiredPolicies(date);
    }
}
