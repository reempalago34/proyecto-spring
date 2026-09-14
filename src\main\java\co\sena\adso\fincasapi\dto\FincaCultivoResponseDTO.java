package co.sena.adso.fincasapi.dto;

import co.sena.adso.fincasapi.entity.FincaCultivo;
import java.time.LocalDate;

public record FincaCultivoResponseDTO(
    Long id,
    Long fincaId,
    String fincaNombre,
    Long cultivoId,
    String cultivoNombre,
    Double areaSembradaHa,
    LocalDate fechaSiembra,
    String temporada,
    String estado
) {
    public static FincaCultivoResponseDTO fromEntity(FincaCultivo entity) {
        return new FincaCultivoResponseDTO(
            entity.getId(),
            entity.getFinca().getId(),
            entity.getFinca().getNombre(),
            entity.getCultivo().getId(),
            entity.getCultivo().getNombre(),
            entity.getAreaSembradaHa(),
            entity.getFechaSiembra(),
            entity.getTemporada().name(),
            entity.getEstado().name()
        );
    }
}
