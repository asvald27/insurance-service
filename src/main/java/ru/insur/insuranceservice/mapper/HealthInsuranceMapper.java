package ru.insur.insuranceservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.insur.insuranceservice.db.entity.HealthInsurancePolicy;
import ru.insur.insuranceservice.dto.CreateHealthPolicyRequest;
import ru.insur.insuranceservice.dto.HealthPolicyResponse;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface HealthInsuranceMapper {

    String DRAFT = "DRAFT";

    @Mapping(target = "clientExternalId", source = "request.clientExternalId")
    @Mapping(target = "startDate", source = "request.startDate")
    @Mapping(target = "endDate", source = "request.endDate")
    @Mapping(target = "status", constant = DRAFT)
    @Mapping(target = "premiumAmount", source = "premiumAmount")
    @Mapping(target = "coverageAmount", source = "request.coverageAmount")
    @Mapping(target = "insuredAge", source = "request.insuredAge")
    @Mapping(target = "hasChronicDiseases", source = "request.hasChronicDiseases")
    @Mapping(target = "isSmoker", source = "request.isSmoker")
    @Mapping(target = "coverageType", source = "request.coverageType")
    @Mapping(target = "dentalCoverage", source = "request.dentalCoverage")
    @Mapping(target = "hospitalizationCoverage", source = "request.hospitalizationCoverage")
    @Mapping(target = "medicalCheckupFrequency", source = "request.medicalCheckupFrequency")
    HealthInsurancePolicy toHealthInsurancePolicy(CreateHealthPolicyRequest request, BigDecimal premiumAmount);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "policyNumber", source = "policyNumber")
    @Mapping(target = "clientExternalId", source = "clientExternalId")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "premiumAmount", source = "premiumAmount")
    @Mapping(target = "coverageAmount", source = "coverageAmount")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "insuredAge", source = "insuredAge")
    @Mapping(target = "hasChronicDiseases", source = "hasChronicDiseases")
    @Mapping(target = "isSmoker", source = "isSmoker")
    @Mapping(target = "coverageType", source = "coverageType")
    @Mapping(target = "dentalCoverage", source = "dentalCoverage")
    @Mapping(target = "hospitalizationCoverage", source = "hospitalizationCoverage")
    @Mapping(target = "medicalCheckupFrequency", source = "medicalCheckupFrequency")
    HealthPolicyResponse toHealthPolicyResponse(HealthInsurancePolicy policy);
}
