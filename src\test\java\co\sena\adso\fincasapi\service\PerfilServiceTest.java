package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.PerfilRequestDTO;
import co.sena.adso.fincasapi.dto.PerfilResponseDTO;
import co.sena.adso.fincasapi.entity.Perfil;
import co.sena.adso.fincasapi.entity.Usuario;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.PerfilRepository;
import co.sena.adso.fincasapi.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
class PerfilServiceTest {

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PerfilService perfilService;

    private Usuario usuario;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("carlos@email.com", "123456", "Carlos Rueda");
        usuario.setId(1L);
        perfil = new Perfil("https://foto.com/foto.jpg", "Bio de prueba", LocalDate.of(1990, 5, 15), "3001234567");
        perfil.setId(1L);
        perfil.setUsuario(usuario);
    }

    @Test
    void create_shouldSaveAndReturnDTO() {
        PerfilRequestDTO dto = new PerfilRequestDTO("https://foto.com/nueva.jpg", "Nueva bio", LocalDate.of(2000, 1, 1), "3009998888");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfil);
        PerfilResponseDTO result = perfilService.create(1L, dto);
        assertThat(result).isNotNull();
        verify(perfilRepository, times(1)).save(any(Perfil.class));
    }

    @Test
    void create_shouldThrowException_whenUsuarioNotFound() {
        PerfilRequestDTO dto = new PerfilRequestDTO("https://foto.com/foto.jpg", "Bio", LocalDate.of(2000, 1, 1), "3009998888");
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> perfilService.create(99L, dto))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void update_shouldModifyAndReturnDTO() {
        PerfilRequestDTO dto = new PerfilRequestDTO("https://foto.com/actualizada.jpg", "Bio actualizada", LocalDate.of(1995, 5, 20), "3001112233");
        when(perfilRepository.findById(1L)).thenReturn(Optional.of(perfil));
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfil);
        PerfilResponseDTO result = perfilService.update(1L, dto);
        assertThat(result).isNotNull();
        verify(perfilRepository, times(1)).save(any(Perfil.class));
    }

    @Test
    void update_shouldThrowException_whenPerfilNotFound() {
        PerfilRequestDTO dto = new PerfilRequestDTO("https://foto.com/foto.jpg", "Bio", LocalDate.of(2000, 1, 1), "3009998888");
        when(perfilRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> perfilService.update(99L, dto))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }
}
