package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.PerfilRequestDTO;
import co.sena.adso.fincasapi.dto.PerfilResponseDTO;
import co.sena.adso.fincasapi.entity.Perfil;
import co.sena.adso.fincasapi.entity.Usuario;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.PerfilRepository;
import co.sena.adso.fincasapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    public PerfilService(PerfilRepository perfilRepository, UsuarioRepository usuarioRepository) {
        this.perfilRepository = perfilRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public PerfilResponseDTO create(@org.springframework.lang.NonNull Long usuarioId, PerfilRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", usuarioId));
        Perfil perfil = new Perfil(dto.fotoUrl(), dto.bio(), dto.fechaNacimiento(), dto.telefono());
        perfil.setUsuario(usuario);
        return PerfilResponseDTO.fromEntity(perfilRepository.save(perfil));
    }

    @Transactional(readOnly = true)
    public PerfilResponseDTO findById(@org.springframework.lang.NonNull Long id) {
        Perfil perfil = perfilRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Perfil", id));
        return PerfilResponseDTO.fromEntity(perfil);
    }

    @Transactional
    public PerfilResponseDTO update(@org.springframework.lang.NonNull Long perfilId, PerfilRequestDTO dto) {
        Perfil perfil = perfilRepository.findById(perfilId)
            .orElseThrow(() -> new ResourceNotFoundException("Perfil", perfilId));
        perfil.setFotoUrl(dto.fotoUrl());
        perfil.setBio(dto.bio());
        perfil.setFechaNacimiento(dto.fechaNacimiento());
        perfil.setTelefono(dto.telefono());
        return PerfilResponseDTO.fromEntity(perfilRepository.save(perfil));
    }
}
