package ru.insur.insuranceservice.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record ClientInfoDto(
        @NotNull UUID id,

        @NotBlank @Size(max = 100) String fullName,

        @NotBlank @Email String email,

        @NotBlank @Size(max = 20) String phoneNumber,

        @NotNull @Past LocalDate birthDate,

        @NotBlank @Size(max = 200) String address,

        @NotNull LocalDateTime createdAt,

        @NotNull LocalDateTime updatedAt,

        // Дополнительные поля для медицинского страхования
        @Size(max = 20) String passportNumber,

        @Size(max = 14) String snils, // СНИЛС

        String medicalHistory, // История болезней (если доступно)

        Boolean hasDisability, // Инвалидность

        @Size(max = 10) String bloodType // Группа крови
) {
    public ClientInfoDto {
        // Валидация в компактном конструкторе
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("fullName не может быть пустым");
        }
        if (birthDate.isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("Клиент должен быть старше 18 лет");
        }
    }

    // Вспомогательные методы
    public Optional<String> getPassportNumber() {
        return Optional.ofNullable(passportNumber);
    }

    public Optional<String> getSnils() {
        return Optional.ofNullable(snils);
    }

    public Optional<String> getMedicalHistory() {
        return Optional.ofNullable(medicalHistory);
    }

    public Optional<Boolean> getHasDisability() {
        return Optional.ofNullable(hasDisability);
    }

    public Optional<String> getBloodType() {
        return Optional.ofNullable(bloodType);
    }

    // Factory метод для тестирования
    public static ClientInfoDto createBasic(
            UUID id,
            String fullName,
            String email,
            LocalDate birthDate
    ) {
        return new ClientInfoDto(
                id,
                fullName,
                email,
                "+79991234567",
                birthDate,
                "г. Москва",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                null,
                null,
                null,
                null
        );
    }
}
