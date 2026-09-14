package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.PerfilRequestDTO;
import co.sena.adso.fincasapi.dto.PerfilResponseDTO;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.service.JwtService;
import co.sena.adso.fincasapi.service.PerfilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("null")
@WebMvcTest(controllers = PerfilController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class PerfilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PerfilService perfilService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crear_shouldReturnCreatedStatus() throws Exception {
        PerfilRequestDTO request = new PerfilRequestDTO("https://foto.com/foto.jpg", "Bio", LocalDate.of(1990, 5, 15), "3001234567");
        PerfilResponseDTO response = new PerfilResponseDTO(1L, "https://foto.com/foto.jpg", "Bio", LocalDate.of(1990, 5, 15), "3001234567");
        when(perfilService.create(eq(1L), any())).thenReturn(response);

        mockMvc.perform(post("/api/perfiles/usuario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crear_shouldReturn404_whenUsuarioNotFound() throws Exception {
        PerfilRequestDTO request = new PerfilRequestDTO("https://foto.com/foto.jpg", "Bio", LocalDate.of(1990, 5, 15), "3001234567");
        when(perfilService.create(eq(99L), any())).thenThrow(new ResourceNotFoundException("Usuario", 99L));

        mockMvc.perform(post("/api/perfiles/usuario/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void actualizar_shouldReturnDTO() throws Exception {
        PerfilRequestDTO request = new PerfilRequestDTO("https://foto.com/nueva.jpg", "Bio actualizada", LocalDate.of(1995, 5, 20), "3001112233");
        PerfilResponseDTO response = new PerfilResponseDTO(1L, "https://foto.com/nueva.jpg", "Bio actualizada", LocalDate.of(1995, 5, 20), "3001112233");
        when(perfilService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/perfiles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.bio").value("Bio actualizada"));
    }

    @Test
    void actualizar_shouldReturn404_whenPerfilNotFound() throws Exception {
        PerfilRequestDTO request = new PerfilRequestDTO("https://foto.com/foto.jpg", "Bio", LocalDate.of(1990, 5, 15), "3001234567");
        when(perfilService.update(eq(99L), any())).thenThrow(new ResourceNotFoundException("Perfil", 99L));

        mockMvc.perform(put("/api/perfiles/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound());
    }
}
