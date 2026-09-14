package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.ProductoRequestDTO;
import co.sena.adso.fincasapi.dto.ProductoResponseDTO;
import co.sena.adso.fincasapi.entity.Categoria;
import co.sena.adso.fincasapi.entity.Producto;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.CategoriaRepository;
import co.sena.adso.fincasapi.repository.ProductoRepository;
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
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria("Lácteos", "Productos lácteos");
        categoria.setId(1L);
        producto = new Producto("Queso", 2500.0, 100, categoria);
        producto.setId(1L);
    }

    @Test
    void findAll_shouldReturnListOfDTOs() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        List<ProductoResponseDTO> result = productoService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).nombre()).isEqualTo("Queso");
    }

    @Test
    void findById_shouldReturnDTO_whenProductoExists() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        ProductoResponseDTO result = productoService.findById(1L);
        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    void findById_shouldThrowException_whenProductoNotFound() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productoService.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void create_shouldSaveAndReturnDTO() {
        ProductoRequestDTO dto = new ProductoRequestDTO("Leche", 3200.0, 50, 1L);
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        ProductoResponseDTO result = productoService.create(dto);
        assertThat(result).isNotNull();
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void create_shouldThrowException_whenCategoriaNotFound() {
        ProductoRequestDTO dto = new ProductoRequestDTO("Leche", 3200.0, 50, 99L);
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productoService.create(dto))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void update_shouldModifyAndReturnDTO() {
        ProductoRequestDTO dto = new ProductoRequestDTO("Queso Fresco", 2800.0, 80, 1L);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        ProductoResponseDTO result = productoService.update(1L, dto);
        assertThat(result).isNotNull();
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void update_shouldThrowException_whenProductoNotFound() {
        ProductoRequestDTO dto = new ProductoRequestDTO("Nope", 100.0, 1, 1L);
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productoService.update(99L, dto))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void delete_shouldThrowException_whenProductoNotFound() {
        when(productoRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> productoService.delete(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_shouldDelete_whenProductoExists() {
        when(productoRepository.existsById(1L)).thenReturn(true);
        productoService.delete(1L);
        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByCategoriaId_shouldReturnFilteredList() {
        when(productoRepository.findByCategoriaId(1L)).thenReturn(List.of(producto));
        List<ProductoResponseDTO> result = productoService.findByCategoriaId(1L);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).nombre()).isEqualTo("Queso");
    }
}
