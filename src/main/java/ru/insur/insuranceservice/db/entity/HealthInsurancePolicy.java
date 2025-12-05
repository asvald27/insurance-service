package ru.insur.insuranceservice.db.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.insur.insuranceservice.enums.PolicyStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "health_insurance_policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthInsurancePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "policy_number", nullable = false, unique = true, length = 50)
    private String policyNumber;

    @Column(name = "client_external_id", nullable = false)
    private UUID clientExternalId;

    // Общие поля
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PolicyStatus status;

    @Column(name = "premium_amount", precision = 15, scale = 2)
    private BigDecimal premiumAmount;

    @Column(name = "coverage_amount", precision = 15, scale = 2)
    private BigDecimal coverageAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Специфичные для здоровья
    @Column(name = "insured_age")
    private Integer insuredAge;

    @Column(name = "has_chronic_diseases")
    private Boolean hasChronicDiseases;

    @Column(name = "is_smoker")
    private Boolean isSmoker;

    @Column(name = "coverage_type", length = 50)
    private String coverageType; // "BASIC", "EXTENDED", "FAMILY"

    @Column(name = "dental_coverage")
    private Boolean dentalCoverage;

    @Column(name = "hospitalization_coverage")
    private Boolean hospitalizationCoverage;

    @Column(name = "medical_checkup_frequency")
    private Integer medicalCheckupFrequency; // раз в N месяцев

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.policyNumber == null) {
            this.policyNumber = "HLTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}