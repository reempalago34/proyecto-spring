package co.sena.adso.fincasapi.specification;

import co.sena.adso.fincasapi.entity.Finca;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class FincaSpecification {

    public static Specification<Finca> withFilters(
            String municipio, String propietario, Double hectareasMin, String nombre) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (municipio != null && !municipio.isBlank()) {
                predicates.add(cb.equal(root.get("municipio"), municipio));
            }
            if (propietario != null && !propietario.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("propietario")),
                    "%" + propietario.toLowerCase() + "%"));
            }
            if (hectareasMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("hectareas"), hectareasMin));
            }
            if (nombre != null && !nombre.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nombre")),
                    "%" + nombre.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
