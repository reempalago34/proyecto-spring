package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.FincaRequestDTO;
import co.sena.adso.fincasapi.dto.FincaResponseDTO;
import co.sena.adso.fincasapi.service.FincaService;
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
@WebMvcTest(controllers = FincaController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class FincaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FincaService fincaService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_shouldReturnListOfDTOs() throws Exception {
        FincaResponseDTO dto = new FincaResponseDTO(1L, "La Esperanza", "Carlos", "El Gualilo", "Vélez", 12.5);
        when(fincaService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/fincas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombre").value("La Esperanza"));
    }

    @Test
    void obtener_shouldReturnDTO_whenFincaExists() throws Exception {
        FincaResponseDTO dto = new FincaResponseDTO(1L, "La Esperanza", "Carlos", "El Gualilo", "Vélez", 12.5);
        when(fincaService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/fincas/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crear_shouldReturnCreatedStatus() throws Exception {
        FincaRequestDTO request = new FincaRequestDTO("Nueva", "Juan", "San José", "Suaita", 5.0);
        FincaResponseDTO response = new FincaResponseDTO(2L, "Nueva", "Juan", "San José", "Suaita", 5.0);
        when(fincaService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/fincas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(2));
    }
}
