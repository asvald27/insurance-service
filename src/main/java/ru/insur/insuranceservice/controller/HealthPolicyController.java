package ru.insur.insuranceservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.insur.insuranceservice.dto.CreateHealthPolicyRequest;
import ru.insur.insuranceservice.dto.HealthPoliceByStatusResponse;
import ru.insur.insuranceservice.dto.HealthExpiredPolicyResponse;
import ru.insur.insuranceservice.dto.HealthPolicyResponse;
import ru.insur.insuranceservice.service.InsuranceService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/health-policies")
@RequiredArgsConstructor
@Validated
public class HealthPolicyController {

    private final InsuranceService insuranceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HealthPolicyResponse createPolicy(@Valid @RequestBody CreateHealthPolicyRequest request) {
        return insuranceService.createPolicy(request);
    }

    @PatchMapping("/{policyId}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activatePolicy(@PathVariable UUID policyId) {
        insuranceService.activatePolicy(policyId);
    }

    @GetMapping("/{policyId}")
    public HealthPolicyResponse getPolicy(@PathVariable UUID policyId) {
        return insuranceService.getPolicy(policyId);
    }

    @GetMapping("/{clientId}")
    public HealthPolicyResponse getPolicyByClientId(@PathVariable UUID clientId) {
        return insuranceService.getPolicyByClientId(clientId);
    }

    @GetMapping("/{status}")
    public HealthPoliceByStatusResponse getPolicyByStatus(@PathVariable String status) {
        return insuranceService.getPoliciesByStatus(status);
    }

    @GetMapping("/{date}")
    public HealthExpiredPolicyResponse getPolicyByDate(@PathVariable String date) {
        return insuranceService.getExpiredPolicies(date);
    }
}