package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.*;
import co.sena.adso.fincasapi.service.FincaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Los Controllers (Controladores) son la puerta de entrada a nuestra API.
 * Su única responsabilidad es recibir peticiones HTTP, llamar al Service (la lógica)
 * y devolver la respuesta HTTP correcta (JSON).
 * 
 * @RestController: Le dice a Spring Boot que esta clase va a manejar peticiones REST. 
 *                  Automáticamente convierte las respuestas a formato JSON.
 * @RequestMapping: Establece la URL base para todos los métodos de esta clase.
 *                  En este caso, todas las rutas empezarán por /api/fincas.
 */
@RestController
@RequestMapping("/api/fincas")
public class FincaController {

    // Dependencia del servicio que contiene la lógica de negocio real
    private final FincaService fincaService;

    // Inyección de dependencias mediante el constructor (Recomendado por Spring)
    public FincaController(FincaService fincaService) {
        this.fincaService = fincaService;
    }

    /**
     * @GetMapping responde a peticiones HTTP GET a la ruta base (/api/fincas).
     * Se usa para "Leer" (Read) información sin modificar nada en la base de datos.
     */
    @GetMapping
    public List<FincaResponseDTO> listar() {
        return fincaService.findAll();
    }

    /**
     * @GetMapping("/paginado") responde a GET /api/fincas/paginado
     * Retorna una página de resultados, lo que es mejor para el rendimiento
     * cuando se tienen miles de registros.
     */
    @GetMapping("/paginado")
    public Page<FincaResponseDTO> listarPaginado(Pageable pageable) {
        return fincaService.findAllPaginado(pageable);
    }

    /**
     * @GetMapping("/buscar") responde a GET /api/fincas/buscar
     * @RequestParam extrae parámetros de la URL, ejemplo: /api/fincas/buscar?municipio=Pitalito
     * (required = false) significa que el parámetro es opcional.
     */
    @GetMapping("/buscar")
    public List<FincaResponseDTO> buscar(
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) String propietario,
            @RequestParam(required = false) Double hectareasMin) {
        return fincaService.buscar(municipio, propietario, hectareasMin);
    }

    /**
     * @GetMapping("/{id}") responde a GET /api/fincas/1
     * @PathVariable extrae el valor "{id}" directamente de la ruta (URL).
     */
    @GetMapping("/{id}")
    public FincaResponseDTO obtener(@PathVariable Long id) {
        return fincaService.findById(id);
    }

    /**
     * @PostMapping responde a peticiones HTTP POST, se usa para "Crear" (Create) un recurso.
     * @RequestBody extrae los datos JSON que vienen en el cuerpo (body) de la petición HTTP.
     * @Valid obliga a que se validen las reglas que le pusimos al DTO (por ejemplo, nombre no nulo).
     * ResponseEntity permite devolver un código de estado específico (201 CREATED en este caso).
     */
    @PostMapping
    public ResponseEntity<FincaResponseDTO> crear(@Valid @RequestBody FincaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fincaService.create(dto));
    }

    /**
     * @PutMapping("/{id}") responde a HTTP PUT para "Actualizar" (Update) un recurso.
     * Combina @PathVariable (para saber QUÉ finca actualizar) y @RequestBody (con los nuevos datos).
     */
    @PutMapping("/{id}")
    public FincaResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody FincaRequestDTO dto) {
        return fincaService.update(id, dto);
    }

    /**
     * @DeleteMapping("/{id}") responde a HTTP DELETE para "Borrar" (Delete) un recurso.
     * @ResponseStatus(HttpStatus.NO_CONTENT) hace que, si todo sale bien, la respuesta sea un
     * código 204 sin contenido, lo cual es el estándar REST al borrar algo exitosamente.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        fincaService.delete(id);
    }
}
