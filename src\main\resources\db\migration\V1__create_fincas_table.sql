-- V1__create_fincas_table.sql
CREATE TABLE fincas (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    propietario VARCHAR(100) NOT NULL,
    vereda VARCHAR(100) NOT NULL,
    municipio VARCHAR(100) NOT NULL,
    hectareas DOUBLE PRECISION NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_finca_municipio ON fincas (municipio);
CREATE INDEX idx_finca_propietario ON fincas (propietario);
