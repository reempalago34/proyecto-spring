package co.sena.adso.fincasapi.dto;

public record AuthResponseDTO(
    String token,
    String type,
    String email,
    String nombre,
    long expiresIn
) {}