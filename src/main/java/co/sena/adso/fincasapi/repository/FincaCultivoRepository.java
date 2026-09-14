package co.sena.adso.fincasapi.repository;

import co.sena.adso.fincasapi.entity.FincaCultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FincaCultivoRepository extends JpaRepository<FincaCultivo, Long> {
    List<FincaCultivo> findByFincaId(Long fincaId);
    List<FincaCultivo> findByCultivoIdOrderByAreaSembradaHaDesc(Long cultivoId);

    @Query("SELECT fc FROM FincaCultivo fc JOIN FETCH fc.finca JOIN FETCH fc.cultivo")
    List<FincaCultivo> findAllWithFincaAndCultivo();
}
