package ru.insur.insuranceservice.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.insur.insuranceservice.db.entity.HealthInsurancePolicy;
import ru.insur.insuranceservice.enums.PolicyStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface HealthInsurancePolicyRepository extends JpaRepository<HealthInsurancePolicy, UUID> {

    HealthInsurancePolicy findByClientExternalId(UUID clientId);

    List<HealthInsurancePolicy> findByStatus(PolicyStatus status);

    @Query("SELECT p FROM HealthInsurancePolicy p WHERE p.endDate > :date AND p.status = 'EXPIRED'")
    List<HealthInsurancePolicy> findExpiredPolicies(@Param("date") LocalDate date);
}
