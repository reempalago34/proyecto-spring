package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.CultivoRequestDTO;
import co.sena.adso.fincasapi.dto.CultivoResponseDTO;
import co.sena.adso.fincasapi.service.CultivoService;
import co.sena.adso.fincasapi.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("null")
@WebMvcTest(controllers = CultivoController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class CultivoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CultivoService cultivoService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_shouldReturnListOfDTOs() throws Exception {
        CultivoResponseDTO dto = new CultivoResponseDTO(1L, "Café Arábica", "permanente", 365);
        when(cultivoService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/cultivos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Café Arábica"));
    }

    @Test
    void obtener_shouldReturnDTO_whenCultivoExists() throws Exception {
        CultivoResponseDTO dto = new CultivoResponseDTO(1L, "Café Arábica", "permanente", 365);
        when(cultivoService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/cultivos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crear_shouldReturnCreatedStatus() throws Exception {
        CultivoRequestDTO request = new CultivoRequestDTO("Plátano", "transitorio", 180);
        CultivoResponseDTO response = new CultivoResponseDTO(2L, "Plátano", "transitorio", 180);
        when(cultivoService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/cultivos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(2));
    }
}
