package ru.insur.insuranceservice.dto;

import java.util.List;

public record AutoPolicyByStatusResponse(
        List<AutoPolicyResponse> autoPolicyResponses
) {
}
