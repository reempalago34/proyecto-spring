package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.*;
import co.sena.adso.fincasapi.service.CultivoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cultivos")
public class CultivoController {

    private final CultivoService cultivoService;

    public CultivoController(CultivoService cultivoService) {
        this.cultivoService = cultivoService;
    }

    @GetMapping
    public List<CultivoResponseDTO> listar() {
        return cultivoService.findAll();
    }

    @GetMapping("/{id}")
    public CultivoResponseDTO obtener(@PathVariable Long id) {
        return cultivoService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CultivoResponseDTO> crear(@Valid @RequestBody CultivoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cultivoService.create(dto));
    }

    @PutMapping("/{id}")
    public CultivoResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody CultivoRequestDTO dto) {
        return cultivoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        cultivoService.delete(id);
    }
}
