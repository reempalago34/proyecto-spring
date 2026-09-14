package co.sena.adso.fincasapi.dto;

import jakarta.validation.constraints.*;

public record FincaRequestDTO(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")
    String nombre,

    @NotBlank(message = "El propietario es obligatorio")
    String propietario,

    @NotBlank(message = "La vereda es obligatoria")
    String vereda,

    @NotBlank(message = "El municipio es obligatorio")
    String municipio,

    @Positive(message = "Las hectáreas deben ser positivas")
    @NotNull(message = "Las hectáreas son obligatorias")
    Double hectareas
) {}
