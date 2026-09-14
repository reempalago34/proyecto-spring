package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.CategoriaRequestDTO;
import co.sena.adso.fincasapi.dto.CategoriaResponseDTO;
import co.sena.adso.fincasapi.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías", description = "Ejemplo de relación 1:N con Productos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    @Operation(summary = "Listar categorías", description = "Retorna todas las categorías con su conteo de productos")
    public List<CategoriaResponseDTO> listar() { return categoriaService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID")
    public CategoriaResponseDTO obtener(@PathVariable Long id) { return categoriaService.findById(id); }

    @PostMapping
    @Operation(summary = "Crear categoría")
    public ResponseEntity<CategoriaResponseDTO> crear(@Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría")
    public CategoriaResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO dto) {
        return categoriaService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar categoría")
    public void eliminar(@PathVariable Long id) { categoriaService.delete(id); }
}
