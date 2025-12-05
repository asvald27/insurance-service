package ru.insur.insuranceservice.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.insur.insuranceservice.db.entity.HealthInsurancePolicy;

import java.util.UUID;

public interface HealthInsurancePolicyRepository extends JpaRepository<HealthInsurancePolicy, UUID> {
}
