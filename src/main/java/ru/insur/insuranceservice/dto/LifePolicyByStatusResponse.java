package ru.insur.insuranceservice.dto;

import java.util.List;

public record LifePolicyByStatusResponse(
        List<LifePolicyResponse> lifePolicyResponses
) {
}
