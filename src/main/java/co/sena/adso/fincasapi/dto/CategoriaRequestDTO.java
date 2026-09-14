package co.sena.adso.fincasapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "Máximo 80 caracteres")
    String nombre,

    @Size(max = 250, message = "Máximo 250 caracteres")
    String descripcion
) {}
