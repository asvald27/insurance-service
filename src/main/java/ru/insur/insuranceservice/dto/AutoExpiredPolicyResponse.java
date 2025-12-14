package ru.insur.insuranceservice.dto;

import java.util.List;

public record AutoExpiredPolicyResponse(
        List<AutoPolicyResponse> autoPolicyResponses
) {
}
