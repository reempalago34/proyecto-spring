package co.sena.adso.fincasapi.repository;

import co.sena.adso.fincasapi.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaId(Long categoriaId);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    long countByCategoriaId(Long categoriaId);
}
