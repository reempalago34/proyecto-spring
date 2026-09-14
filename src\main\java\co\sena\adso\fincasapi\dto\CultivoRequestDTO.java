package co.sena.adso.fincasapi.dto;

import jakarta.validation.constraints.*;

public record CultivoRequestDTO(
    @NotBlank(message = "El nombre del cultivo es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")
    String nombre,

    @NotBlank(message = "El tipo de cultivo es obligatorio (permanente/transitorio)")
    String tipo,

    @Positive(message = "El ciclo en días debe ser positivo")
    @NotNull(message = "El ciclo en días es obligatorio")
    Integer cicloDias
) {}
