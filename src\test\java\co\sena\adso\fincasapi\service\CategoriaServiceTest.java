package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.CategoriaRequestDTO;
import co.sena.adso.fincasapi.dto.CategoriaResponseDTO;
import co.sena.adso.fincasapi.entity.Categoria;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.CategoriaRepository;
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
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria("Lácteos", "Productos lácteos");
        categoria.setId(1L);
    }

    @Test
    void findAll_shouldReturnListOfDTOs() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
        List<CategoriaResponseDTO> result = categoriaService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).nombre()).isEqualTo("Lácteos");
    }

    @Test
    void findById_shouldReturnDTO_whenCategoriaExists() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        CategoriaResponseDTO result = categoriaService.findById(1L);
        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    void findById_shouldThrowException_whenCategoriaNotFound() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> categoriaService.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void create_shouldSaveAndReturnDTO() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Nueva", "Descripción");
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        CategoriaResponseDTO result = categoriaService.create(dto);
        assertThat(result).isNotNull();
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void update_shouldModifyAndReturnDTO_whenCategoriaExists() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Actualizada", "Nueva desc");
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        CategoriaResponseDTO result = categoriaService.update(1L, dto);
        assertThat(result).isNotNull();
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void update_shouldThrowException_whenCategoriaNotFound() {
        CategoriaRequestDTO dto = new CategoriaRequestDTO("Nope", "No existe");
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> categoriaService.update(99L, dto))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void delete_shouldThrowException_whenCategoriaNotFound() {
        when(categoriaRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> categoriaService.delete(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_shouldDelete_whenCategoriaExists() {
        when(categoriaRepository.existsById(1L)).thenReturn(true);
        categoriaService.delete(1L);
        verify(categoriaRepository, times(1)).deleteById(1L);
    }
}
