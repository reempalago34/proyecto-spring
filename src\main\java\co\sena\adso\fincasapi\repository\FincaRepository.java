package co.sena.adso.fincasapi.repository;

import co.sena.adso.fincasapi.entity.Finca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FincaRepository extends JpaRepository<Finca, Long>,
                                         JpaSpecificationExecutor<Finca> {
    List<Finca> findByMunicipio(String municipio);
    List<Finca> findByNombreContainingIgnoreCase(String nombre);

    @Query(value = "SELECT * FROM fincas WHERE hectareas > :min", nativeQuery = true)
    List<Finca> findFincasWithMinHectareas(@Param("min") Double min);
}
