package co.sena.adso.fincasapi.dto;

import co.sena.adso.fincasapi.entity.Categoria;

public record CategoriaResponseDTO(
    Long id,
    String nombre,
    String descripcion,
    int totalProductos
) {
    public static CategoriaResponseDTO fromEntity(Categoria entity) {
        return new CategoriaResponseDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getDescripcion(),
            entity.getProductos().size()
        );
    }
}
