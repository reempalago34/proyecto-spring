package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.UsuarioRequestDTO;
import co.sena.adso.fincasapi.dto.UsuarioResponseDTO;
import co.sena.adso.fincasapi.entity.Usuario;
import co.sena.adso.fincasapi.exception.BusinessException;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("carlos@email.com", "123456", "Carlos Rueda");
        usuario.setId(1L);
    }

    @Test
    void findAll_shouldReturnListOfDTOs() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        List<UsuarioResponseDTO> result = usuarioService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).nombre()).isEqualTo("Carlos Rueda");
    }

    @Test
    void findById_shouldReturnDTO_whenUsuarioExists() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        UsuarioResponseDTO result = usuarioService.findById(1L);
        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    void findById_shouldThrowException_whenUsuarioNotFound() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> usuarioService.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void create_shouldSaveAndReturnDTO() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("nuevo@email.com", "abcdef", "Nuevo Usuario");
        when(usuarioRepository.existsByEmail("nuevo@email.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        UsuarioResponseDTO result = usuarioService.create(dto);
        assertThat(result).isNotNull();
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void create_shouldThrowBusinessException_whenEmailDuplicado() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("carlos@email.com", "abcdef", "Otro");
        when(usuarioRepository.existsByEmail("carlos@email.com")).thenReturn(true);
        assertThatThrownBy(() -> usuarioService.create(dto))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("carlos@email.com");
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void findByEmail_shouldReturnDTO_whenFound() {
        when(usuarioRepository.findByEmail("carlos@email.com")).thenReturn(Optional.of(usuario));
        UsuarioResponseDTO result = usuarioService.findByEmail("carlos@email.com");
        assertThat(result.email()).isEqualTo("carlos@email.com");
    }

    @Test
    void findByEmail_shouldThrowException_whenNotFound() {
        when(usuarioRepository.findByEmail("noexiste@email.com")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> usuarioService.findByEmail("noexiste@email.com"))
            .isInstanceOf(ResourceNotFoundException.class);
    }
}
