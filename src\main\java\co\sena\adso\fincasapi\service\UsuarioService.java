package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.UsuarioRequestDTO;
import co.sena.adso.fincasapi.dto.UsuarioResponseDTO;
import co.sena.adso.fincasapi.entity.Usuario;
import co.sena.adso.fincasapi.exception.BusinessException;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
            .map(UsuarioResponseDTO::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(@org.springframework.lang.NonNull Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new BusinessException("El email " + dto.email() + " ya está registrado");
        }
        Usuario usuario = new Usuario(dto.email(), dto.password(), dto.nombre());
        return UsuarioResponseDTO.fromEntity(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario con email " + email, 0L));
        return UsuarioResponseDTO.fromEntity(usuario);
    }
}
