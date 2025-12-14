package ru.insur.insuranceservice.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.insur.insuranceservice.db.entity.AutoInsurancePolicy;
import ru.insur.insuranceservice.enums.PolicyStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AutoInsurancePolicyRepository extends JpaRepository<AutoInsurancePolicy, UUID> {

    AutoInsurancePolicy findByClientExternalId(UUID clientId);

    List<AutoInsurancePolicy> findByStatus(PolicyStatus status);

    @Query("SELECT p FROM AutoInsurancePolicy p WHERE p.endDate > :date AND p.status = 'EXPIRED'")
    List<AutoInsurancePolicy> findExpiredPolicies(@Param("date") LocalDate date);
}
