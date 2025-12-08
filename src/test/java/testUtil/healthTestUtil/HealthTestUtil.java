package testUtil.healthTestUtil;

import ru.insur.insuranceservice.db.entity.HealthInsurancePolicy;
import ru.insur.insuranceservice.dto.CreateHealthPolicyRequest;
import ru.insur.insuranceservice.dto.HealthPolicyResponse;
import ru.insur.insuranceservice.enums.PolicyStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class HealthTestUtil {

    // ========== UUID константы для тестирования ==========
    public static final UUID TEST_CLIENT_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    public static final UUID TEST_POLICY_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");

    // ========== Фиксированные даты для тестирования ==========
    public static final LocalDate FIXED_START_DATE = LocalDate.of(2025, 11, 28);
    public static final LocalDate FIXED_END_DATE = LocalDate.of(2026, 12, 8);
    public static final LocalDateTime FIXED_CREATED_AT = LocalDateTime.of(2025, 11, 28, 19, 50, 36, 582961300);
    public static final LocalDateTime FIXED_UPDATED_AT = LocalDateTime.of(2025, 12, 8, 19, 50, 36, 582961300);

    // ========== CreateHealthPolicyRequest методы ==========

    public static CreateHealthPolicyRequest createValidHealthPolicyRequest() {
        return new CreateHealthPolicyRequest(
                TEST_CLIENT_ID,
                FIXED_START_DATE.plusDays(1), // Завтра от фиксированной даты
                FIXED_END_DATE,
                new BigDecimal("1000000.00"),
                30,
                false,
                false,
                "BASIC",
                true,
                true,
                6
        );
    }

    public static CreateHealthPolicyRequest createHealthPolicyRequestWithAllDefaults() {
        return new CreateHealthPolicyRequest(
                TEST_CLIENT_ID,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusYears(1),
                new BigDecimal("500000.00"),
                35,
                null, // Все null -> будут использованы значения по умолчанию
                null,
                null,
                null,
                null,
                null
        );
    }

    public static CreateHealthPolicyRequest createHealthPolicyRequestForSmoker() {
        return new CreateHealthPolicyRequest(
                TEST_CLIENT_ID,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusYears(1),
                new BigDecimal("750000.00"),
                45,
                false,
                true, // Курящий
                "EXTENDED",
                true,
                true,
                12
        );
    }

    public static CreateHealthPolicyRequest createHealthPolicyRequestWithChronicDiseases() {
        return new CreateHealthPolicyRequest(
                TEST_CLIENT_ID,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusYears(1),
                new BigDecimal("800000.00"),
                55,
                true, // Хронические заболевания
                false,
                "BASIC",
                false,
                true,
                3
        );
    }

    public static CreateHealthPolicyRequest createFamilyHealthPolicyRequest() {
        return new CreateHealthPolicyRequest(
                TEST_CLIENT_ID,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusYears(1),
                new BigDecimal("2000000.00"),
                40,
                false,
                false,
                "FAMILY", // Семейное покрытие
                true,
                true,
                12
        );
    }

    public static CreateHealthPolicyRequest createExpiredHealthPolicyRequest() {
        return new CreateHealthPolicyRequest(
                TEST_CLIENT_ID,
                LocalDate.now().minusYears(1), // Год назад
                LocalDate.now().minusMonths(6), // 6 месяцев назад
                new BigDecimal("600000.00"),
                30,
                false,
                false,
                "BASIC",
                false,
                false,
                0
        );
    }

    // ========== HealthInsurancePolicy методы ==========

    public static HealthInsurancePolicy createValidHealthInsurancePolicy() {
        return HealthInsurancePolicy.builder()
                .id(TEST_POLICY_ID)
                .policyNumber("HLTH-TEST123")
                .clientExternalId(TEST_CLIENT_ID)
                .startDate(FIXED_START_DATE)
                .endDate(FIXED_END_DATE)
                .status(PolicyStatus.ACTIVE)
                .premiumAmount(new BigDecimal("50000.00"))
                .coverageAmount(new BigDecimal("1000000.00"))
                .createdAt(FIXED_CREATED_AT)
                .updatedAt(FIXED_UPDATED_AT)
                .insuredAge(30)
                .hasChronicDiseases(false)
                .isSmoker(false)
                .coverageType("BASIC")
                .dentalCoverage(true)
                .hospitalizationCoverage(true)
                .medicalCheckupFrequency(6)
                .build();
    }

    public static HealthInsurancePolicy createDraftHealthInsurancePolicy() {
        return HealthInsurancePolicy.builder()
                .id(UUID.randomUUID())
                .policyNumber("HLTH-DRAFT456")
                .clientExternalId(TEST_CLIENT_ID)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusYears(1))
                .status(PolicyStatus.DRAFT)
                .premiumAmount(new BigDecimal("30000.00"))
                .coverageAmount(new BigDecimal("500000.00"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .insuredAge(25)
                .hasChronicDiseases(false)
                .isSmoker(false)
                .coverageType("BASIC")
                .dentalCoverage(false)
                .hospitalizationCoverage(true)
                .medicalCheckupFrequency(12)
                .build();
    }

    public static HealthInsurancePolicy createExpiredHealthInsurancePolicy() {
        return HealthInsurancePolicy.builder()
                .id(UUID.randomUUID())
                .policyNumber("HLTH-EXP789")
                .clientExternalId(TEST_CLIENT_ID)
                .startDate(LocalDate.now().minusYears(2))
                .endDate(LocalDate.now().minusMonths(6))
                .status(PolicyStatus.EXPIRED)
                .premiumAmount(new BigDecimal("40000.00"))
                .coverageAmount(new BigDecimal("800000.00"))
                .createdAt(LocalDateTime.now().minusYears(2))
                .updatedAt(LocalDateTime.now().minusMonths(6))
                .insuredAge(40)
                .hasChronicDiseases(true)
                .isSmoker(true)
                .coverageType("EXTENDED")
                .dentalCoverage(true)
                .hospitalizationCoverage(true)
                .medicalCheckupFrequency(3)
                .build();
    }

    public static HealthInsurancePolicy createHealthInsurancePolicyForClient(UUID clientId) {
        return HealthInsurancePolicy.builder()
                .id(UUID.randomUUID())
                .policyNumber("HLTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .clientExternalId(clientId)
                .startDate(LocalDate.now().minusMonths(1))
                .endDate(LocalDate.now().plusMonths(11))
                .status(PolicyStatus.ACTIVE)
                .premiumAmount(new BigDecimal("25000.00"))
                .coverageAmount(new BigDecimal("300000.00"))
                .createdAt(LocalDateTime.now().minusMonths(1))
                .updatedAt(LocalDateTime.now())
                .insuredAge(35)
                .hasChronicDiseases(false)
                .isSmoker(false)
                .coverageType("BASIC")
                .dentalCoverage(false)
                .hospitalizationCoverage(false)
                .medicalCheckupFrequency(0)
                .build();
    }

    public static HealthInsurancePolicy createHealthInsurancePolicyWithFutureStart() {
        return HealthInsurancePolicy.builder()
                .id(UUID.randomUUID())
                .policyNumber("HLTH-FUTURE")
                .clientExternalId(TEST_CLIENT_ID)
                .startDate(LocalDate.now().plusMonths(1))
                .endDate(LocalDate.now().plusYears(2))
                .status(PolicyStatus.DRAFT)
                .premiumAmount(new BigDecimal("60000.00"))
                .coverageAmount(new BigDecimal("1500000.00"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .insuredAge(50)
                .hasChronicDiseases(false)
                .isSmoker(true)
                .coverageType("EXTENDED")
                .dentalCoverage(true)
                .hospitalizationCoverage(true)
                .medicalCheckupFrequency(6)
                .build();
    }

    // ========== HealthPolicyResponse методы ==========

    public static HealthPolicyResponse createValidHealthPolicyResponse() {
        return new HealthPolicyResponse(
                TEST_POLICY_ID,
                "HLTH-TEST123",
                TEST_CLIENT_ID,
                FIXED_START_DATE,
                FIXED_END_DATE,
                PolicyStatus.ACTIVE,
                new BigDecimal("50000.00"),
                new BigDecimal("1000000.00"),
                FIXED_CREATED_AT,
                FIXED_UPDATED_AT,
                30,
                false,
                false,
                "BASIC",
                true,
                true,
                6
        );
    }

    public static HealthPolicyResponse createHealthPolicyResponseWithId(UUID policyId) {
        return new HealthPolicyResponse(
                policyId,
                "HLTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                TEST_CLIENT_ID,
                LocalDate.now().minusMonths(1),
                LocalDate.now().plusMonths(11),
                PolicyStatus.ACTIVE,
                new BigDecimal("35000.00"),
                new BigDecimal("750000.00"),
                LocalDateTime.now().minusMonths(1),
                LocalDateTime.now(),
                28,
                false,
                false,
                "BASIC",
                false,
                true,
                12
        );
    }

    public static HealthPolicyResponse createExpiredHealthPolicyResponse() {
        return new HealthPolicyResponse(
                UUID.randomUUID(),
                "HLTH-EXPIRED",
                TEST_CLIENT_ID,
                LocalDate.now().minusYears(1),
                LocalDate.now().minusDays(1),
                PolicyStatus.EXPIRED,
                new BigDecimal("45000.00"),
                new BigDecimal("900000.00"),
                LocalDateTime.now().minusYears(1),
                LocalDateTime.now().minusDays(1),
                42,
                true,
                true,
                "EXTENDED",
                true,
                true,
                3
        );
    }

    // ========== Вспомогательные методы ==========

    /**
     * Создает список из нескольких полисов для одного клиента
     */
    public static java.util.List<HealthInsurancePolicy> createMultiplePoliciesForClient(int count, UUID clientId) {
        java.util.List<HealthInsurancePolicy> policies = new java.util.ArrayList<>();

        for (int i = 0; i < count; i++) {
            policies.add(
                    HealthInsurancePolicy.builder()
                            .id(UUID.randomUUID())
                            .policyNumber("HLTH-" + clientId.toString().substring(0, 4) + "-" + i)
                            .clientExternalId(clientId)
                            .startDate(LocalDate.now().minusMonths(i))
                            .endDate(LocalDate.now().plusYears(1).minusMonths(i))
                            .status(i % 2 == 0 ? PolicyStatus.ACTIVE : PolicyStatus.DRAFT)
                            .premiumAmount(new BigDecimal(20000 + i * 5000))
                            .coverageAmount(new BigDecimal(500000 + i * 100000))
                            .createdAt(LocalDateTime.now().minusMonths(i))
                            .updatedAt(LocalDateTime.now().minusMonths(i))
                            .insuredAge(25 + i * 5)
                            .hasChronicDiseases(i % 3 == 0)
                            .isSmoker(i % 4 == 0)
                            .coverageType(i % 3 == 0 ? "BASIC" : i % 3 == 1 ? "EXTENDED" : "FAMILY")
                            .dentalCoverage(i % 2 == 0)
                            .hospitalizationCoverage(i % 3 != 0)
                            .medicalCheckupFrequency(6 + i)
                            .build()
            );
        }

        return policies;
    }

    /**
     * Создает полис с заданным статусом
     */
    public static HealthInsurancePolicy createPolicyWithStatus(PolicyStatus status) {
        return HealthInsurancePolicy.builder()
                .id(UUID.randomUUID())
                .policyNumber("HLTH-STATUS-" + status.name())
                .clientExternalId(TEST_CLIENT_ID)
                .startDate(LocalDate.now().minusMonths(1))
                .endDate(LocalDate.now().plusMonths(11))
                .status(status)
                .premiumAmount(new BigDecimal("30000.00"))
                .coverageAmount(new BigDecimal("600000.00"))
                .createdAt(LocalDateTime.now().minusMonths(1))
                .updatedAt(LocalDateTime.now())
                .insuredAge(33)
                .hasChronicDiseases(false)
                .isSmoker(false)
                .coverageType("BASIC")
                .dentalCoverage(false)
                .hospitalizationCoverage(true)
                .medicalCheckupFrequency(0)
                .build();
    }

    /**
     * Создает запрос с минимальными данными
     */
    public static CreateHealthPolicyRequest createMinimalHealthPolicyRequest() {
        return new CreateHealthPolicyRequest(
                TEST_CLIENT_ID,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusYears(1),
                new BigDecimal("100000.00"),
                20,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
