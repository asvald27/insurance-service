--liquibase formatted sql

--changeset nwmxask:create_health_policies_table
CREATE TABLE health_insurance_policies (
                                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                           policy_number VARCHAR(50) NOT NULL UNIQUE,
                                           client_external_id UUID NOT NULL,
                                           start_date DATE NOT NULL,
                                           end_date DATE NOT NULL,
                                           status VARCHAR(20) NOT NULL CHECK (status IN ('DRAFT', 'ACTIVE', 'EXPIRED', 'CANCELLED', 'PENDING_PAYMENT', 'SUSPENDED')),
                                           premium_amount DECIMAL(15,2),
                                           coverage_amount DECIMAL(15,2),
                                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           insured_age INTEGER,
                                           has_chronic_diseases BOOLEAN DEFAULT false,
                                           is_smoker BOOLEAN DEFAULT false,
                                           coverage_type VARCHAR(50),
                                           dental_coverage BOOLEAN DEFAULT false,
                                           hospitalization_coverage BOOLEAN DEFAULT false,
                                           medical_checkup_frequency INTEGER
);

--changeset nwmxask:create_health_policies_indexes
CREATE INDEX idx_health_policy_client ON health_insurance_policies(client_external_id);
CREATE INDEX idx_health_policy_status ON health_insurance_policies(status);
CREATE INDEX idx_health_policy_number ON health_insurance_policies(policy_number);