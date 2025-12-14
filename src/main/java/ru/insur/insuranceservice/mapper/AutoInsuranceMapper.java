package ru.insur.insuranceservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.insur.insuranceservice.db.entity.AutoInsurancePolicy;
import ru.insur.insuranceservice.dto.AutoPolicyResponse;
import ru.insur.insuranceservice.dto.CreateAutoPolicyRequest;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface AutoInsuranceMapper {

    @Mapping(target = "clientExternalId", source = "request.clientExternalId")
    @Mapping(target = "startDate", source = "request.startDate")
    @Mapping(target = "endDate", source = "request.endDate")
    @Mapping(target = "coverageAmount", source = "request.coverageAmount")
    @Mapping(target = "premiumAmount", source = "premiumAmount")
    @Mapping(target = "driverAge", source = "request.driverAge")
    @Mapping(target = "vehicleVin", source = "request.vehicleVin")
    @Mapping(target = "vehicleModel", source = "request.vehicleModel")
    @Mapping(target = "vehicleYear", source = "request.vehicleYear")
    @Mapping(target = "driverExperienceYears", source = "request.driverExperienceYears")
    @Mapping(target = "hasAccidentHistory", source = "request.hasAccidentHistory")
    @Mapping(target = "enginePowerHp", source = "request.enginePowerHp")
    @Mapping(target = "driverLicenseCategory", source = "request.driverLicenseCategory")
    AutoInsurancePolicy toAutoInsurancePolicy(CreateAutoPolicyRequest request, BigDecimal premiumAmount);

    @Mapping(target = "id", source = "policy.id")
    @Mapping(target = "policyNumber", source = "policy.policyNumber")
    @Mapping(target = "clientExternalId", source = "policy.clientExternalId")
    @Mapping(target = "startDate", source = "policy.startDate")
    @Mapping(target = "endDate", source = "policy.endDate")
    @Mapping(target = "status", source = "policy.status")
    @Mapping(target = "premiumAmount", source = "policy.premiumAmount")
    @Mapping(target = "coverageAmount", source = "policy.coverageAmount")
    @Mapping(target = "createdAt", source = "policy.createdAt")
    @Mapping(target = "updatedAt", source = "policy.updatedAt")
    AutoPolicyResponse toAutoPolicyResponse(AutoInsurancePolicy policy);
}
