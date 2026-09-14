package co.sena.adso.fincasapi.config;

import co.sena.adso.fincasapi.entity.Finca;
import co.sena.adso.fincasapi.repository.FincaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * DataSeeder es el encargado de insertar datos iniciales en la base de datos
 * la primera vez que arranca la aplicación. 
 * 
 * Implementar CommandLineRunner hace que el método run() se ejecute
 * automáticamente justo después de que Spring Boot haya iniciado completamente.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    // Spring inyectará automáticamente este repositorio
    private final FincaRepository fincaRepository;

    public DataSeeder(FincaRepository fincaRepository) {
        this.fincaRepository = fincaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Solo insertamos datos si la tabla de fincas está vacía
        if (fincaRepository.count() == 0) {
            System.out.println("🌱 [DataSeeder] La tabla fincas está vacía. Insertando datos de prueba...");

            Finca finca1 = new Finca("La Primavera", "Carlos Mario Pérez", "El Vergel", "Pitalito", 15.5);
            Finca finca2 = new Finca("El Retiro", "Ana Lucía Gómez", "Las Palmas", "Pitalito", 8.2);
            Finca finca3 = new Finca("Villa Clara", "José Ignacio Restrepo", "San Antonio", "San Agustín", 20.0);

            // saveAll permite guardar una lista completa de entidades de una sola vez
            fincaRepository.saveAll(List.of(finca1, finca2, finca3));

            System.out.println("✅ [DataSeeder] Se insertaron 3 fincas con éxito.");
        } else {
            System.out.println("ℹ️ [DataSeeder] La base de datos ya contiene información. No se insertaron nuevas fincas.");
        }
    }
}
