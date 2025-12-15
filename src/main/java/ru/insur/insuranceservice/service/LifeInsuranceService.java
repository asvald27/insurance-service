package ru.insur.insuranceservice.service;

import ru.insur.insuranceservice.dto.CreateLifePolicyRequest;
import ru.insur.insuranceservice.dto.LifeExpiredPolicyResponse;
import ru.insur.insuranceservice.dto.LifePolicyByStatusResponse;
import ru.insur.insuranceservice.dto.LifePolicyResponse;

import java.util.UUID;

public interface LifeInsuranceService extends BaseInsuranceService{
    /**
     * Создание полиса
     * @param request тело запроса на создание полиса
     * @return ответ сервиса
     */
    LifePolicyResponse createPolicy(CreateLifePolicyRequest request);

    /**
     * Получение полиса по идентификатору
     * @param policyId идентификатор полиса в формате UUID
     * @return полис клиента
     */
    LifePolicyResponse getPolicy(UUID policyId);

    /**
     * Получение полиса по идентификатору клиента
     * @param clientId идентификатор клиента в формате UUID
     * @return полис клиента
     */
    LifePolicyResponse getPolicyByClientId(UUID clientId);

    /**
     * Получение списка полисов страхования жизни в зависимости от статуса
     * @param status запрашиваемый статус
     * @return список полисов страхования здоровья с актуальным статусом
     */
    LifePolicyByStatusResponse getPoliciesByStatus(String status);

    /**
     * Получение списка просроченных полисов страхования здоровья
     * @param date дата
     * @return список полисов страхования здоровья со статусом EXPIRED
     */
    LifeExpiredPolicyResponse getExpiredPolicies(String date);
}
