package ru.insur.insuranceservice.dto;

import java.util.List;

public record HealthPoliceByStatusResponse(
        List<HealthPolicyResponse> policies
) {
}
