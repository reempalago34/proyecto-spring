package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.FincaCultivoRequestDTO;
import co.sena.adso.fincasapi.dto.FincaCultivoResponseDTO;
import co.sena.adso.fincasapi.enums.Estado;
import co.sena.adso.fincasapi.enums.Temporada;
import co.sena.adso.fincasapi.service.FincaCultivoService;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("null")
@WebMvcTest(controllers = FincaCultivoController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class FincaCultivoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FincaCultivoService fcService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_shouldReturnListOfDTOs() throws Exception {
        FincaCultivoResponseDTO dto = new FincaCultivoResponseDTO(1L, 1L, "La Esperanza", 1L, "Café", 5.0, LocalDate.of(2026, 3, 15), "PRIMAVERA", "ACTIVO");
        when(fcService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/finca-cultivos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void obtener_shouldReturnDTO_whenExists() throws Exception {
        FincaCultivoResponseDTO dto = new FincaCultivoResponseDTO(1L, 1L, "La Esperanza", 1L, "Café", 5.0, LocalDate.of(2026, 3, 15), "PRIMAVERA", "ACTIVO");
        when(fcService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/finca-cultivos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void listarPorFinca_shouldReturnList() throws Exception {
        FincaCultivoResponseDTO dto = new FincaCultivoResponseDTO(1L, 1L, "La Esperanza", 1L, "Café", 5.0, LocalDate.of(2026, 3, 15), "PRIMAVERA", "ACTIVO");
        when(fcService.findByFincaId(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/finca-cultivos/finca/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].fincaId").value(1));
    }

    @Test
    void listarPorCultivo_shouldReturnList() throws Exception {
        FincaCultivoResponseDTO dto = new FincaCultivoResponseDTO(1L, 1L, "La Esperanza", 1L, "Café", 5.0, LocalDate.of(2026, 3, 15), "PRIMAVERA", "ACTIVO");
        when(fcService.findByCultivoId(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/finca-cultivos/cultivo/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].cultivoId").value(1));
    }

    @Test
    void crear_shouldReturnCreatedStatus() throws Exception {
        FincaCultivoRequestDTO request = new FincaCultivoRequestDTO(1L, 1L, 5.0, LocalDate.of(2026, 3, 15), Temporada.PRIMAVERA, Estado.ACTIVO);
        FincaCultivoResponseDTO response = new FincaCultivoResponseDTO(2L, 1L, "La Esperanza", 1L, "Café", 5.0, LocalDate.of(2026, 3, 15), "PRIMAVERA", "ACTIVO");
        when(fcService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/finca-cultivos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(2));
    }
}
