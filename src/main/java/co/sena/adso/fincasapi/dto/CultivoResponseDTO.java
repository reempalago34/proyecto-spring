package co.sena.adso.fincasapi.dto;

import co.sena.adso.fincasapi.entity.Cultivo;

public record CultivoResponseDTO(
    Long id,
    String nombre,
    String tipo,
    Integer cicloDias
) {
    public static CultivoResponseDTO fromEntity(Cultivo entity) {
        return new CultivoResponseDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getTipo(),
            entity.getCicloDias()
        );
    }
}
