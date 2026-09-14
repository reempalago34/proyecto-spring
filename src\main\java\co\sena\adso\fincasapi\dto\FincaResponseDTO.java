package co.sena.adso.fincasapi.dto;

import co.sena.adso.fincasapi.entity.Finca;

public record FincaResponseDTO(
    Long id,
    String nombre,
    String propietario,
    String vereda,
    String municipio,
    Double hectareas
) {
    public static FincaResponseDTO fromEntity(Finca entity) {
        return new FincaResponseDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getPropietario(),
            entity.getVereda(),
            entity.getMunicipio(),
            entity.getHectareas()
        );
    }
}
