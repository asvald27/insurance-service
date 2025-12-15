package ru.insur.insuranceservice.db.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.insur.insuranceservice.enums.PolicyStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность для хранения информации о полисах страхования жизни.
 * Предназначена для защиты финансовых интересов близких в случае смерти застрахованного.
 */
@Entity
@Table(name = "life_insurance_policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LifeInsurancePolicy {

    /**
     * Уникальный идентификатор полиса (UUID).
     * Генерируется автоматически при сохранении новой записи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Уникальный номер полиса в человекочитаемом формате.
     * Формат: "LIFE-" + 8 случайных символов (например, "LIFE-X9Y8Z7W6").
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
     * Страховая защита начинается с этой даты.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * Дата окончания действия страхового покрытия.
     * Для бессрочного страхования жизни может быть далеким будущим.
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
     * Страховая премия - регулярный платеж клиента за страхование жизни.
     * Рассчитывается на основе возраста, профессии, хобби и срока страхования.
     * Хранится с точностью до 2 десятичных знаков.
     */
    @Column(name = "premium_amount", precision = 15, scale = 2)
    private BigDecimal premiumAmount;

    /**
     * Страховая сумма - сумма, выплачиваемая выгодоприобретателю
     * при наступлении страхового случая (смерти застрахованного).
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
     * Обновляется при изменениях данных полиса.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- СПЕЦИФИЧНЫЕ ПОЛЯ ДЛЯ СТРАХОВАНИЯ ЖИЗНИ ---

    /**
     * Возраст застрахованного лица на момент оформления полиса.
     * Один из ключевых факторов при расчете премии.
     * Обычно ограничен (например, от 18 до 70 лет).
     */
    @Column(name = "insured_age")
    private Integer insuredAge;

    /**
     * ФИО выгодоприобретателя - лица, которое получит страховую выплату.
     * Может быть родственником, супругом/супругой или любым другим лицом.
     */
    @Column(name = "beneficiary_name", length = 200)
    private String beneficiaryName;

    /**
     * Отношение выгодоприобретателя к застрахованному.
     * Например: "супруг", "дочь", "сын", "брат", "мать", "друг".
     * Используется для проверок и статистики.
     */
    @Column(name = "beneficiary_relation", length = 50)
    private String beneficiaryRelation;

    /**
     * Срок действия полиса в годах.
     * Для срочного страхования жизни.
     * Для бессрочного страхования может быть null или 0.
     */
    @Column(name = "policy_term_years")
    private Integer policyTermYears;

    /**
     * Флаг опасной профессии застрахованного.
     * true - профессия связана с повышенным риском (пожарный, пилот, шахтер и т.д.)
     * Влияет на стоимость полиса (повышающий коэффициент).
     */
    @Column(name = "is_high_risk_occupation")
    private Boolean isHighRiskOccupation;

    /**
     * Флаг наличия опасных хобби у застрахованного.
     * true - занимается экстремальными видами спорта (альпинизм, дайвинг, автогонки и т.д.)
     * Влияет на стоимость полиса (повышающий коэффициент).
     */
    @Column(name = "has_dangerous_hobbies")
    private Boolean hasDangerousHobbies;

    /**
     * Тип страхового покрытия жизни.
     * Возможные значения:
     * - "TERM": срочное страхование жизни (на определенный срок)
     * - "WHOLE_LIFE": пожизненное страхование (до смерти)
     * - "INVESTMENT": инвестиционное страхование жизни (с элементом инвестирования)
     */
    @Column(name = "coverage_type", length = 50)
    private String coverageType;

    /**
     * Callback-метод, выполняемый перед сохранением новой сущности в БД.
     * Автоматически устанавливает даты создания/обновления и генерирует номер полиса.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.policyNumber == null) {
            this.policyNumber = "LIFE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}