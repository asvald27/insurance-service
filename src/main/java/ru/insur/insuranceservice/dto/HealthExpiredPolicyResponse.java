package ru.insur.insuranceservice.dto;

import java.util.List;

public record HealthExpiredPolicyResponse(
        List<HealthPolicyResponse> policies
) {
}
