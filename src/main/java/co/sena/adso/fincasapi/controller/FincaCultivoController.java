package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.*;
import co.sena.adso.fincasapi.service.FincaCultivoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/finca-cultivos")
public class FincaCultivoController {

    private final FincaCultivoService fcService;

    public FincaCultivoController(FincaCultivoService fcService) {
        this.fcService = fcService;
    }

    @GetMapping
    public List<FincaCultivoResponseDTO> listar() {
        return fcService.findAll();
    }

    @GetMapping("/{id}")
    public FincaCultivoResponseDTO obtener(@PathVariable Long id) {
        return fcService.findById(id);
    }

    @GetMapping("/finca/{fincaId}")
    public List<FincaCultivoResponseDTO> listarPorFinca(@PathVariable Long fincaId) {
        return fcService.findByFincaId(fincaId);
    }

    @GetMapping("/cultivo/{cultivoId}")
    public List<FincaCultivoResponseDTO> listarPorCultivo(@PathVariable Long cultivoId) {
        return fcService.findByCultivoId(cultivoId);
    }

    @PostMapping
    public ResponseEntity<FincaCultivoResponseDTO> crear(@Valid @RequestBody FincaCultivoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fcService.create(dto));
    }

    @PutMapping("/{id}")
    public FincaCultivoResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody FincaCultivoRequestDTO dto) {
        return fcService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        fcService.delete(id);
    }
}
