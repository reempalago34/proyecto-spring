package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.PerfilRequestDTO;
import co.sena.adso.fincasapi.dto.PerfilResponseDTO;
import co.sena.adso.fincasapi.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfiles")
@Tag(name = "Perfiles", description = "Ejemplo de relación 1:1 con Usuarios")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @PostMapping("/usuario/{usuarioId}")
    @Operation(summary = "Crear perfil para un usuario (relación 1:1)")
    public ResponseEntity<PerfilResponseDTO> crear(
            @PathVariable Long usuarioId,
            @Valid @RequestBody PerfilRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.create(usuarioId, dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar perfil")
    public PerfilResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody PerfilRequestDTO dto) {
        return perfilService.update(id, dto);
    }
}
