package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.AuthRequestDTO;
import co.sena.adso.fincasapi.dto.AuthResponseDTO;
import co.sena.adso.fincasapi.service.AuthService;
import co.sena.adso.fincasapi.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("null")
@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_shouldReturnToken_whenCredentialsValid() throws Exception {
        AuthRequestDTO request = new AuthRequestDTO("carlos@email.com", "123456");
        when(authService.login(any())).thenReturn(new AuthResponseDTO("token.jwt.x", "Bearer", "carlos@email.com", "Carlos Rueda", 86400000L));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("token.jwt.x"))
            .andExpect(jsonPath("$.email").value("carlos@email.com"))
            .andExpect(jsonPath("$.type").value("Bearer"));
    }

    @Test
    void login_shouldReturn401_whenCredentialsInvalid() throws Exception {
        when(authService.login(any())).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new AuthRequestDTO("carlos@email.com", "mala"))))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void login_shouldReturn400_whenBodyInvalido() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }
}