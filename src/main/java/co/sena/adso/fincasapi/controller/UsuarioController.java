package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.UsuarioRequestDTO;
import co.sena.adso.fincasapi.dto.UsuarioResponseDTO;
import co.sena.adso.fincasapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Ejemplo de relación 1:1 con Perfiles")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios")
    public List<UsuarioResponseDTO> listar() { return usuarioService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    public UsuarioResponseDTO obtener(@PathVariable Long id) { return usuarioService.findById(id); }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuario por email")
    public UsuarioResponseDTO buscarPorEmail(@PathVariable String email) {
        return usuarioService.findByEmail(email);
    }

    @PostMapping
    @Operation(summary = "Crear usuario")
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(dto));
    }
}
