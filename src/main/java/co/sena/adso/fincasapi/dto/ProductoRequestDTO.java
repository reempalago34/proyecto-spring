package co.sena.adso.fincasapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductoRequestDTO(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "Máximo 120 caracteres")
    String nombre,

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    Double precio,

    @NotNull(message = "El stock es obligatorio")
    @Positive(message = "El stock debe ser positivo")
    Integer stock,

    @NotNull(message = "La categoría es obligatoria")
    @org.springframework.lang.NonNull
    Long categoriaId
) {}
