package co.sena.adso.fincasapi.controller;

import co.sena.adso.fincasapi.dto.ProductoRequestDTO;
import co.sena.adso.fincasapi.dto.ProductoResponseDTO;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.service.JwtService;
import co.sena.adso.fincasapi.service.ProductoService;
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
@WebMvcTest(controllers = ProductoController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_shouldReturnListOfDTOs() throws Exception {
        ProductoResponseDTO dto = new ProductoResponseDTO(1L, "Queso", 2500.0, 100, 1L, "Lácteos");
        when(productoService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/productos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Queso"));
    }

    @Test
    void obtener_shouldReturnDTO_whenProductoExists() throws Exception {
        ProductoResponseDTO dto = new ProductoResponseDTO(1L, "Queso", 2500.0, 100, 1L, "Lácteos");
        when(productoService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/productos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtener_shouldReturn404_whenProductoNotFound() throws Exception {
        when(productoService.findById(99L)).thenThrow(new ResourceNotFoundException("Producto", 99L));

        mockMvc.perform(get("/api/productos/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void listarPorCategoria_shouldReturnFilteredList() throws Exception {
        ProductoResponseDTO dto = new ProductoResponseDTO(1L, "Queso", 2500.0, 100, 1L, "Lácteos");
        when(productoService.findByCategoriaId(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/productos/categoria/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].categoriaNombre").value("Lácteos"));
    }

    @Test
    void crear_shouldReturnCreatedStatus() throws Exception {
        ProductoRequestDTO request = new ProductoRequestDTO("Leche", 3200.0, 50, 1L);
        ProductoResponseDTO response = new ProductoResponseDTO(2L, "Leche", 3200.0, 50, 1L, "Lácteos");
        when(productoService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void crear_shouldReturn400_whenInvalidBody() throws Exception {
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_shouldReturnDTO() throws Exception {
        ProductoRequestDTO request = new ProductoRequestDTO("Queso Fresco", 2800.0, 80, 1L);
        ProductoResponseDTO response = new ProductoResponseDTO(1L, "Queso Fresco", 2800.0, 80, 1L, "Lácteos");
        when(productoService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Queso Fresco"));
    }

    @Test
    void actualizar_shouldReturn404_whenProductoNotFound() throws Exception {
        ProductoRequestDTO request = new ProductoRequestDTO("Nope", 100.0, 1, 1L);
        when(productoService.update(eq(99L), any())).thenThrow(new ResourceNotFoundException("Producto", 99L));

        mockMvc.perform(put("/api/productos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_shouldReturnNoContent() throws Exception {
        doNothing().when(productoService).delete(1L);

        mockMvc.perform(delete("/api/productos/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_shouldReturn404_whenProductoNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Producto", 99L)).when(productoService).delete(99L);

        mockMvc.perform(delete("/api/productos/99"))
            .andExpect(status().isNotFound());
    }
}
