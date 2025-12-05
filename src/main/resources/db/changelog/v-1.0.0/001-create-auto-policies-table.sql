--liquibase formatted sql

--changeset nwmxask:create_auto_policies_table
CREATE TABLE auto_insurance_policies (
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
                                         vehicle_vin VARCHAR(50),
                                         vehicle_model VARCHAR(100),
                                         vehicle_year INTEGER,
                                         driver_experience_years INTEGER,
                                         has_accident_history BOOLEAN DEFAULT false,
                                         engine_power_hp INTEGER,
                                         driver_age INTEGER,
                                         driver_license_category VARCHAR(10)
);

--changeset nwmxask:create_auto_policies_indexes
CREATE INDEX idx_auto_policy_client ON auto_insurance_policies(client_external_id);
CREATE INDEX idx_auto_policy_status ON auto_insurance_policies(status);
CREATE INDEX idx_auto_policy_number ON auto_insurance_policies(policy_number);
CREATE INDEX idx_auto_policy_dates ON auto_insurance_policies(start_date, end_date);