package ru.insur.insuranceservice.db.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.insur.insuranceservice.enums.PolicyStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность для хранения информации о медицинских страховых полисах.
 * Предназначена для страхования здоровья и медицинских расходов клиентов.
 */
@Entity
@Table(name = "health_insurance_policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthInsurancePolicy {

    /**
     * Уникальный идентификатор полиса (UUID).
     * Генерируется автоматически при сохранении новой записи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Уникальный номер полиса в человекочитаемом формате.
     * Формат: "HLTH-" + 8 случайных символов (например, "HLTH-A1B2C3D4").
     * Генерируется автоматически через @PrePersist, если не указан явно.
     */
    @Column(name = "policy_number", nullable = false, unique = true, length = 50)
    private String policyNumber;

    /**
     * Внешний идентификатор клиента из микросервиса клиентов.
     * Используется для связи с внешней системой управления клиентами.
     */
    @Column(name = "client_external_id", nullable = false)
    private UUID clientExternalId;

    // --- ОБЩИЕ ПОЛЯ ДЛЯ ВСЕХ ТИПОВ ПОЛИСОВ ---

    /**
     * Дата начала действия страхового покрытия.
     * Клиент защищен с этой даты включительно.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * Дата окончания действия страхового покрытия.
     * Клиент защищен до этой даты включительно.
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * Текущий статус полиса.
     * Возможные значения: DRAFT, ACTIVE, EXPIRED, CANCELLED, PENDING_PAYMENT, SUSPENDED.
     * Определяет жизненный цикл полиса.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PolicyStatus status;

    /**
     * Страховая премия - сумма, которую клиент платит за страховку.
     * Рассчитывается на основе рисковых факторов (возраст, здоровье и т.д.).
     * Хранится с точностью до 2 десятичных знаков.
     */
    @Column(name = "premium_amount", precision = 15, scale = 2)
    private BigDecimal premiumAmount;

    /**
     * Страховая сумма - максимальная сумма, которую страховая компания
     * выплатит при наступлении страхового случая.
     * Хранится с точностью до 2 десятичных знаков.
     */
    @Column(name = "coverage_amount", precision = 15, scale = 2)
    private BigDecimal coverageAmount;

    /**
     * Дата и время создания записи о полисе в системе.
     * Заполняется автоматически при создании, не изменяется при обновлениях.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления записи о полисе.
     * Обновляется автоматически при любых изменениях данных полиса.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- СПЕЦИФИЧНЫЕ ПОЛЯ ДЛЯ МЕДИЦИНСКОГО СТРАХОВАНИЯ ---

    /**
     * Возраст застрахованного лица на момент оформления полиса.
     * Используется для расчета страховой премии (чем выше возраст, тем выше риски).
     */
    @Column(name = "insured_age")
    private Integer insuredAge;

    /**
     * Флаг наличия хронических заболеваний у застрахованного.
     * true - есть хронические заболевания, false - нет.
     * Влияет на стоимость полиса и условия выплат.
     */
    @Column(name = "has_chronic_diseases")
    private Boolean hasChronicDiseases;

    /**
     * Флаг курения застрахованного лица.
     * true - курит, false - не курит.
     * Курильщики имеют повышенные риски заболеваний, что влияет на стоимость полиса.
     */
    @Column(name = "is_smoker")
    private Boolean isSmoker;

    /**
     * Тип страхового покрытия.
     * Возможные значения:
     * - "BASIC": базовое покрытие (амбулаторное лечение)
     * - "EXTENDED": расширенное покрытие (стационар + амбулаторно)
     * - "FAMILY": семейное покрытие (для всей семьи)
     */
    @Column(name = "coverage_type", length = 50)
    private String coverageType;

    /**
     * Флаг включения стоматологического покрытия в полис.
     * true - стоматология включена, false - не включена.
     * Дополнительная опция, увеличивающая стоимость полиса.
     */
    @Column(name = "dental_coverage")
    private Boolean dentalCoverage;

    /**
     * Флаг включения покрытия госпитализации в полис.
     * true - госпитализация включена, false - не включена.
     * Дополнительная опция, покрывающая расходы на стационарное лечение.
     */
    @Column(name = "hospitalization_coverage")
    private Boolean hospitalizationCoverage;

    /**
     * Частота обязательных медицинских осмотров (в месяцах).
     * Например, 6 = осмотр раз в 6 месяцев.
     * 0 = осмотры не требуются.
     * Влияет на стоимость полиса и условия выплат.
     */
    @Column(name = "medical_checkup_frequency")
    private Integer medicalCheckupFrequency;

    /**
     * Callback-метод, выполняемый перед сохранением новой сущности в БД.
     * Автоматически устанавливает даты создания/обновления и генерирует номер полиса.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.policyNumber == null) {
            this.policyNumber = "HLTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}