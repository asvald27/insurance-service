package ru.insur.insuranceservice.service;

import ru.insur.insuranceservice.dto.*;

import java.util.UUID;

public interface AutoInsuranceService extends BaseInsuranceService{

    /**
     * Создание полиса авто страхования
     * @param request тело запроса на создание полиса авто страхования
     * @return полис клиента
     */
    AutoPolicyResponse createPolicy(CreateAutoPolicyRequest request);
    /**
     * Получение полиса по идентификатору
     * @param policyId идентификатор полиса в формате UUID
     * @return полис клиента
     */
    AutoPolicyResponse getPolicy(UUID policyId);

    /**
     * Получение полиса по идентификатору клиента
     * @param clientId идентификатор клиента в формате UUID
     * @return полис клиента
     */
    AutoPolicyResponse getPolicyByClientId(UUID clientId);

    /**
     * Получение списка полисов страхования транспортного средства в зависимости от статуса
     * @param status запрашиваемый статус
     * @return список полисов страхования здоровья с актуальным статусом
     */
    AutoPolicyByStatusResponse getPoliciesByStatus(String status);

    /**
     * Получение списка просроченных полисов страхования транспортного средства
     * @param date дата
     * @return список полисов страхования здоровья со статусом EXPIRED
     */
    AutoExpiredPolicyResponse getExpiredPolicies(String date);
}
