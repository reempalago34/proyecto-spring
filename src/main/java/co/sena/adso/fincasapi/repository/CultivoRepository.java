package co.sena.adso.fincasapi.repository;

import co.sena.adso.fincasapi.entity.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Long> {
    @Query("SELECT c FROM Cultivo c WHERE c.tipo = :tipo AND c.cicloDias <= :maxDias")
    List<Cultivo> findByTipoAndMaxCiclo(@Param("tipo") String tipo, @Param("maxDias") Integer maxDias);
}
