package ru.insur.insuranceservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.insur.insuranceservice.dto.*;
import ru.insur.insuranceservice.service.AutoInsuranceService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auto-policies")
@RequiredArgsConstructor
@Validated
public class AutoPolicyController {

    private final AutoInsuranceService autoInsuranceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutoPolicyResponse createPolicy(@Valid @RequestBody CreateAutoPolicyRequest request) {
        return autoInsuranceService.createPolicy(request);
    }

    @PatchMapping("/{policyId}/activate")
    public void activatePolicy(@PathVariable UUID policyId) {
        autoInsuranceService.activatePolicy(policyId);
    }

    @GetMapping("/{policyId}")
    public AutoPolicyResponse getPolicy(@PathVariable UUID policyId) {
        return autoInsuranceService.getPolicy(policyId);
    }

    @GetMapping("/{clientId}")
    public AutoPolicyResponse getPolicyByClientId(@PathVariable UUID clientId) {
        return autoInsuranceService.getPolicyByClientId(clientId);
    }

    @GetMapping("/{status}")
    public AutoPolicyByStatusResponse getPolicyByStatus(@PathVariable String status) {
        return autoInsuranceService.getPoliciesByStatus(status);
    }

    @GetMapping("/{date}")
    public AutoExpiredPolicyResponse getPolicyByDate(@PathVariable String date) {
        return autoInsuranceService.getExpiredPolicies(date);
    }
}
