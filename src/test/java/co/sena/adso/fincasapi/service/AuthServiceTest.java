package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.AuthRequestDTO;
import co.sena.adso.fincasapi.dto.AuthResponseDTO;
import co.sena.adso.fincasapi.entity.Usuario;
import co.sena.adso.fincasapi.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void login_shouldReturnToken_whenCredentialsValid() {
        Usuario usuario = new Usuario("carlos@email.com", "hash123", "Carlos Rueda");
        when(usuarioRepository.findByEmail("carlos@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("123456", "hash123")).thenReturn(true);
        when(jwtService.generateToken("carlos@email.com")).thenReturn("token.jwt.x");

        AuthResponseDTO result = authService.login(new AuthRequestDTO("carlos@email.com", "123456"));

        assertThat(result.token()).isEqualTo("token.jwt.x");
        assertThat(result.email()).isEqualTo("carlos@email.com");
        assertThat(result.nombre()).isEqualTo("Carlos Rueda");
        assertThat(result.type()).isEqualTo("Bearer");
    }

    @Test
    void login_shouldThrow401_whenEmailNotFound() {
        when(usuarioRepository.findByEmail("nadie@email.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(new AuthRequestDTO("nadie@email.com", "123456")))
            .isInstanceOf(ResponseStatusException.class)
            .extracting(e -> ((ResponseStatusException) e).getStatusCode())
            .isEqualTo(HttpStatus.UNAUTHORIZED);
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void login_shouldThrow401_whenPasswordInvalid() {
        Usuario usuario = new Usuario("carlos@email.com", "hash123", "Carlos Rueda");
        when(usuarioRepository.findByEmail("carlos@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("incorrecta", "hash123")).thenReturn(false);

        assertThatThrownBy(() -> authService.login(new AuthRequestDTO("carlos@email.com", "incorrecta")))
            .isInstanceOf(ResponseStatusException.class)
            .extracting(e -> ((ResponseStatusException) e).getStatusCode())
            .isEqualTo(HttpStatus.UNAUTHORIZED);
        verify(jwtService, never()).generateToken(any());
    }
}