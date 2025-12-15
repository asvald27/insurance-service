package ru.insur.insuranceservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.insur.insuranceservice.db.entity.LifeInsurancePolicy;
import ru.insur.insuranceservice.dto.CreateLifePolicyRequest;
import ru.insur.insuranceservice.dto.LifePolicyResponse;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface LifeInsuranceMapper {

    @Mapping(target = "policyNumber", source = "request.policyNumber")
    @Mapping(target = "clientExternalId", source = "request.clientExternalId")
    @Mapping(target = "startDate", source = "request.startDate")
    @Mapping(target = "endDate", source = "request.endDate")
    @Mapping(target = "coverageAmount", source = "request.coverageAmount")
    @Mapping(target = "premiumAmount", source = "premiumAmount")
    @Mapping(target = "insuredAge", source = "request.insuredAge")
    @Mapping(target = "beneficiaryName", source = "request.beneficiaryName")
    @Mapping(target = "beneficiaryRelation", source = "request.beneficiaryRelation")
    @Mapping(target = "isHighRiskOccupation", source = "request.isHighRiskOccupation")
    @Mapping(target = "hasDangerousHobbies", source = "request.hasDangerousHobbies")
    @Mapping(target = "policyTermYears", source = "request.policyTermYears")
    LifeInsurancePolicy toLifeInsurancePolicy(CreateLifePolicyRequest request, BigDecimal premiumAmount);

    @Mapping(target = "policyNumber", source = "policy.policyNumber")
    @Mapping(target = "clientExternalId", source = "policy.clientExternalId")
    @Mapping(target = "startDate", source = "policy.startDate")
    @Mapping(target = "endDate", source = "policy.endDate")
    @Mapping(target = "status", source = "policy.status")
    @Mapping(target = "premiumAmount", source = "policy.premiumAmount")
    @Mapping(target = "coverageAmount", source = "policy.coverageAmount")
    @Mapping(target = "createdAt", source = "policy.createdAt")
    @Mapping(target = "updatedAt", source = "policy.updatedAt")
    @Mapping(target = "policyTermYears", source = "policy.policyTermYears")
    LifePolicyResponse toLifePolicyResponse(LifeInsurancePolicy policy);
}
