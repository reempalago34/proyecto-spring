-- V3__create_finca_cultivo_table.sql
CREATE TABLE finca_cultivo (
    id BIGSERIAL PRIMARY KEY,
    finca_id BIGINT NOT NULL REFERENCES fincas(id),
    cultivo_id BIGINT NOT NULL REFERENCES cultivos(id),
    area_sembrada_ha DOUBLE PRECISION NOT NULL,
    fecha_siembra DATE NOT NULL,
    temporada VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_fc_finca_id ON finca_cultivo (finca_id);
CREATE INDEX idx_fc_cultivo_id ON finca_cultivo (cultivo_id);
