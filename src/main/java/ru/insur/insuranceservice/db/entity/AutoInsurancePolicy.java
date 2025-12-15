package ru.insur.insuranceservice.db.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.insur.insuranceservice.enums.PolicyStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность для хранения информации о полисах автострахования (КАСКО/ОСАГО).
 * Предназначена для страхования транспортных средств от ущерба, угона и гражданской ответственности.
 */
@Entity
@Table(name = "auto_insurance_policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoInsurancePolicy {

    /**
     * Уникальный идентификатор полиса (UUID).
     * Генерируется автоматически при сохранении новой записи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Уникальный номер полиса в человекочитаемом формате.
     * Формат: "AUTO-" + 8 случайных символов (например, "AUTO-5F6G7H8I").
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
     * Автомобиль защищен с этой даты включительно.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * Дата окончания действия страхового покрытия.
     * Обычно через 1 год от даты начала.
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * Текущий статус полиса.
     * Возможные значения: DRAFT, ACTIVE, EXPIRED, CANCELLED, PENDING_PAYMENT, SUSPENDED.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PolicyStatus status;

    /**
     * Страховая премия - стоимость полиса автострахования.
     * Рассчитывается на основе характеристик авто, стажа водителя, истории аварий.
     * Хранится с точностью до 2 десятичных знаков.
     */
    @Column(name = "premium_amount", precision = 15, scale = 2)
    private BigDecimal premiumAmount;

    /**
     * Страховая сумма - максимальная сумма возмещения ущерба.
     * Для КАСКО - стоимость авто или согласованная сумма.
     * Для ОСАГО - лимиты по закону.
     * Хранится с точностью до 2 десятичных знаков.
     */
    @Column(name = "coverage_amount", precision = 15, scale = 2)
    private BigDecimal coverageAmount;

    /**
     * Дата и время создания записи о полисе в системе.
     * Заполняется автоматически при создании.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления записи о полисе.
     * Обновляется при любых изменениях данных полиса.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- СПЕЦИФИЧНЫЕ ПОЛЯ ДЛЯ АВТОСТРАХОВАНИЯ ---

    /**
     * VIN-номер (Vehicle Identification Number) транспортного средства.
     * Уникальный 17-символьный идентификатор автомобиля.
     * Используется для идентификации конкретного ТС.
     */
    @Column(name = "vehicle_vin", length = 50)
    private String vehicleVin;

    /**
     * Модель и марка транспортного средства.
     * Например: "Toyota Camry", "BMW X5", "Lada Vesta".
     */
    @Column(name = "vehicle_model", length = 100)
    private String vehicleModel;

    /**
     * Год выпуска транспортного средства.
     * Влияет на стоимость полиса (чем старше авто, тем ниже стоимость, но выше риски).
     */
    @Column(name = "vehicle_year")
    private Integer vehicleYear;

    /**
     * Стаж вождения основного водителя в годах.
     * Ключевой фактор при расчете премии (меньше стаж = выше риск).
     */
    @Column(name = "driver_experience_years")
    private Integer driverExperienceYears;

    /**
     * Флаг наличия аварий в истории вождения.
     * true - были аварии по вине водителя, false - безаварийная история.
     * Влияет на стоимость полиса (повышающий коэффициент).
     */
    @Column(name = "has_accident_history")
    private Boolean hasAccidentHistory;

    /**
     * Мощность двигателя в лошадиных силах (л.с.).
     * Влияет на стоимость полиса (чем мощнее авто, тем выше риск и стоимость).
     */
    @Column(name = "engine_power_hp")
    private Integer enginePowerHp;

    /**
     * Возраст основного водителя.
     * Молодые водители (до 25 лет) имеют повышенные коэффициенты.
     */
    @Column(name = "driver_age")
    private Integer driverAge;

    /**
     * Категория водительского удостоверения.
     * Например: "B" - легковые авто, "C" - грузовики, "D" - автобусы.
     * Должна соответствовать категории застрахованного ТС.
     */
    @Column(name = "driver_license_category", length = 10)
    private String driverLicenseCategory;

    /**
     * Callback-метод, выполняемый перед сохранением новой сущности в БД.
     * Автоматически устанавливает даты создания и генерирует номер полиса.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.policyNumber == null) {
            this.policyNumber = "AUTO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }

    /**
     * Callback-метод, выполняемый перед обновлением существующей сущности в БД.
     * Автоматически обновляет дату последнего изменения.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}