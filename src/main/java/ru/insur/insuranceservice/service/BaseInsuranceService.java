package ru.insur.insuranceservice.service;

import java.util.UUID;

public interface BaseInsuranceService {

    /**
     * Активация полиса
     * @param policyId id для активации полиса
     */
    void activatePolicy(UUID policyId);

}
