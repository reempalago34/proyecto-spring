package co.sena.adso.fincasapi.dto;

import co.sena.adso.fincasapi.entity.Perfil;
import java.time.LocalDate;

public record PerfilResponseDTO(
    Long id,
    String fotoUrl,
    String bio,
    LocalDate fechaNacimiento,
    String telefono
) {
    public static PerfilResponseDTO fromEntity(Perfil entity) {
        return new PerfilResponseDTO(
            entity.getId(),
            entity.getFotoUrl(),
            entity.getBio(),
            entity.getFechaNacimiento(),
            entity.getTelefono()
        );
    }
}
