package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.CultivoRequestDTO;
import co.sena.adso.fincasapi.dto.CultivoResponseDTO;
import co.sena.adso.fincasapi.entity.Cultivo;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.CultivoRepository;
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
class CultivoServiceTest {

    @Mock
    private CultivoRepository cultivoRepository;

    @InjectMocks
    private CultivoService cultivoService;

    private Cultivo cultivo;

    @BeforeEach
    void setUp() {
        cultivo = new Cultivo("Café Arábica", "permanente", 365);
        cultivo.setId(1L);
    }

    @Test
    void findAll_shouldReturnListOfDTOs() {
        when(cultivoRepository.findAll()).thenReturn(List.of(cultivo));
        List<CultivoResponseDTO> result = cultivoService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).nombre()).isEqualTo("Café Arábica");
    }

    @Test
    void findById_shouldReturnDTO_whenCultivoExists() {
        when(cultivoRepository.findById(1L)).thenReturn(Optional.of(cultivo));
        CultivoResponseDTO result = cultivoService.findById(1L);
        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    void findById_shouldThrowException_whenCultivoNotFound() {
        when(cultivoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cultivoService.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void create_shouldSaveAndReturnDTO() {
        CultivoRequestDTO dto = new CultivoRequestDTO("Plátano", "transitorio", 180);
        when(cultivoRepository.save(any(Cultivo.class))).thenReturn(cultivo);
        CultivoResponseDTO result = cultivoService.create(dto);
        assertThat(result).isNotNull();
        verify(cultivoRepository, times(1)).save(any(Cultivo.class));
    }

    @Test
    void delete_shouldThrowException_whenNotExists() {
        when(cultivoRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> cultivoService.delete(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }
}
