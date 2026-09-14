package co.sena.adso.fincasapi.dto;

import co.sena.adso.fincasapi.enums.Estado;
import co.sena.adso.fincasapi.enums.Temporada;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record FincaCultivoRequestDTO(
    @NotNull(message = "El ID de la finca es obligatorio")
    @org.springframework.lang.NonNull
    Long fincaId,

    @NotNull(message = "El ID del cultivo es obligatorio")
    @org.springframework.lang.NonNull
    Long cultivoId,

    @Positive(message = "El área sembrada debe ser positiva")
    @NotNull(message = "El área sembrada en hectáreas es obligatoria")
    Double areaSembradaHa,

    @NotNull(message = "La fecha de siembra es obligatoria")
    LocalDate fechaSiembra,

    @NotNull(message = "La temporada es obligatoria")
    Temporada temporada,

    @NotNull(message = "El estado es obligatorio")
    Estado estado
) {}
