package co.sena.adso.fincasapi.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record PerfilRequestDTO(
    @Size(max = 300, message = "Máximo 300 caracteres")
    String fotoUrl,

    @Size(max = 500, message = "Máximo 500 caracteres")
    String bio,

    LocalDate fechaNacimiento,

    @Size(max = 30, message = "Máximo 30 caracteres")
    String telefono
) {}
