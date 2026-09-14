package co.sena.adso.fincasapi.dto;

import co.sena.adso.fincasapi.entity.Producto;

public record ProductoResponseDTO(
    Long id,
    String nombre,
    Double precio,
    Integer stock,
    Long categoriaId,
    String categoriaNombre
) {
    public static ProductoResponseDTO fromEntity(Producto entity) {
        return new ProductoResponseDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getPrecio(),
            entity.getStock(),
            entity.getCategoria().getId(),
            entity.getCategoria().getNombre()
        );
    }
}
