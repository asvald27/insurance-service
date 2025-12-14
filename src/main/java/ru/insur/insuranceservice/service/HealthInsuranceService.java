package ru.insur.insuranceservice.service;

import ru.insur.insuranceservice.dto.CreateHealthPolicyRequest;
import ru.insur.insuranceservice.dto.HealthExpiredPolicyResponse;
import ru.insur.insuranceservice.dto.HealthPoliceByStatusResponse;
import ru.insur.insuranceservice.dto.HealthPolicyResponse;

import java.util.UUID;

public interface HealthInsuranceService extends BaseInsuranceService{

    /**
     * Создание полиса
     * @param request тело запроса на создание полиса
     * @return ответ сервиса
     */
    HealthPolicyResponse createPolicy(CreateHealthPolicyRequest request);
    /**
     * Получение полиса по идентификатору
     * @param policyId идентификатор полиса в формате UUID
     * @return полис клиента
     */
    HealthPolicyResponse getPolicy(UUID policyId);

    /**
     * Получение полиса по идентификатору клиента
     * @param clientId идентификатор клиента в формате UUID
     * @return полис клиента
     */
    HealthPolicyResponse getPolicyByClientId(UUID clientId);

    /**
     * Получение списка полисов страхования здоровья в зависимости от статуса
     * @param status запрашиваемый статус
     * @return список полисов страхования здоровья с актуальным статусом
     */
    HealthPoliceByStatusResponse getPoliciesByStatus(String status);

    /**
     * Получение списка просроченных полисов страхования здоровья
     * @param date дата
     * @return список полисов страхования здоровья со статусом EXPIRED
     */
    HealthExpiredPolicyResponse getExpiredPolicies(String date);
}
