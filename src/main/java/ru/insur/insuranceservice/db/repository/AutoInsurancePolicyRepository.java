package ru.insur.insuranceservice.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.insur.insuranceservice.db.entity.AutoInsurancePolicy;

import java.util.UUID;

public interface AutoInsurancePolicyRepository extends JpaRepository<AutoInsurancePolicy, UUID> {
}
