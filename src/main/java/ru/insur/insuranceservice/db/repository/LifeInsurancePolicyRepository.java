package ru.insur.insuranceservice.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.insur.insuranceservice.db.entity.LifeInsurancePolicy;
import ru.insur.insuranceservice.enums.PolicyStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LifeInsurancePolicyRepository extends JpaRepository<LifeInsurancePolicy, UUID> {
    LifeInsurancePolicy findByClientExternalId(UUID clientId);

    List<LifeInsurancePolicy> findByStatus(PolicyStatus status);

    @Query("SELECT p FROM LifeInsurancePolicy p WHERE p.endDate > :date AND p.status = 'EXPIRED'")
    List<LifeInsurancePolicy> findExpiredPolicies(@Param("date") LocalDate date);
}
