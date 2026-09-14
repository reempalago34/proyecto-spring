package co.sena.adso.fincasapi.dto;

import co.sena.adso.fincasapi.entity.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String email,
    String nombre,
    PerfilResponseDTO perfil
) {
    public static UsuarioResponseDTO fromEntity(Usuario entity) {
        PerfilResponseDTO perfilDTO = entity.getPerfil() != null
            ? PerfilResponseDTO.fromEntity(entity.getPerfil())
            : null;
        return new UsuarioResponseDTO(
            entity.getId(),
            entity.getEmail(),
            entity.getNombre(),
            perfilDTO
        );
    }
}
