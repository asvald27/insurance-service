package ru.insur.insuranceservice.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.insur.insuranceservice.db.entity.LifeInsurancePolicy;

import java.util.UUID;

public interface LifeInsurancePolicyRepository extends JpaRepository<LifeInsurancePolicy, UUID> {
}
