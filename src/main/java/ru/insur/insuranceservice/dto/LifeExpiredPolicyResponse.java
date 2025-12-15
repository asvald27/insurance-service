package ru.insur.insuranceservice.dto;

import java.util.List;

public record LifeExpiredPolicyResponse(
        List<LifePolicyResponse> expiredLifePolicies
) {
}
