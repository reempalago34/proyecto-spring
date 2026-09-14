package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.CategoriaRequestDTO;
import co.sena.adso.fincasapi.dto.CategoriaResponseDTO;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.service.CategoriaService;
import co.sena.adso.fincasapi.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SuppressWarnings("null")
@WebMvcTest(controllers = CategoriaController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_shouldReturnListOfDTOs() throws Exception {
        CategoriaResponseDTO dto = new CategoriaResponseDTO(1L, "Lácteos", "Lácteos y derivados", 0);
        when(categoriaService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/categorias"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Lácteos"));
    }

    @Test
    void obtener_shouldReturnDTO_whenCategoriaExists() throws Exception {
        CategoriaResponseDTO dto = new CategoriaResponseDTO(1L, "Lácteos", "Lácteos y derivados", 0);
        when(categoriaService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/categorias/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtener_shouldReturn404_whenCategoriaNotFound() throws Exception {
        when(categoriaService.findById(99L)).thenThrow(new ResourceNotFoundException("Categoria", 99L));

        mockMvc.perform(get("/api/categorias/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void crear_shouldReturnCreatedStatus() throws Exception {
        CategoriaRequestDTO request = new CategoriaRequestDTO("Nueva", "Descripción");
        CategoriaResponseDTO response = new CategoriaResponseDTO(2L, "Nueva", "Descripción", 0);
        when(categoriaService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void crear_shouldReturn400_whenInvalidBody() throws Exception {
        mockMvc.perform(post("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_shouldReturnDTO() throws Exception {
        CategoriaRequestDTO request = new CategoriaRequestDTO("Actualizada", "Nueva desc");
        CategoriaResponseDTO response = new CategoriaResponseDTO(1L, "Actualizada", "Nueva desc", 0);
        when(categoriaService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Actualizada"));
    }

    @Test
    void actualizar_shouldReturn404_whenCategoriaNotFound() throws Exception {
        CategoriaRequestDTO request = new CategoriaRequestDTO("Nope", "No existe");
        when(categoriaService.update(eq(99L), any())).thenThrow(new ResourceNotFoundException("Categoria", 99L));

        mockMvc.perform(put("/api/categorias/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_shouldReturnNoContent() throws Exception {
        doNothing().when(categoriaService).delete(1L);

        mockMvc.perform(delete("/api/categorias/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_shouldReturn404_whenCategoriaNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Categoria", 99L)).when(categoriaService).delete(99L);

        mockMvc.perform(delete("/api/categorias/99"))
            .andExpect(status().isNotFound());
    }
}
