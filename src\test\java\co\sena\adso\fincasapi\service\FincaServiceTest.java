package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.FincaRequestDTO;
import co.sena.adso.fincasapi.dto.FincaResponseDTO;
import co.sena.adso.fincasapi.entity.Finca;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.FincaRepository;
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
class FincaServiceTest {

    @Mock
    private FincaRepository fincaRepository;

    @InjectMocks
    private FincaService fincaService;

    private Finca finca;

    @BeforeEach
    void setUp() {
        finca = new Finca("La Esperanza", "Carlos Rueda", "El Gualilo", "Vélez", 12.5);
        finca.setId(1L);
    }

    @Test
    void findAll_shouldReturnListOfDTOs() {
        when(fincaRepository.findAll()).thenReturn(List.of(finca));
        List<FincaResponseDTO> result = fincaService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).nombre()).isEqualTo("La Esperanza");
    }

    @Test
    void findById_shouldReturnDTO_whenFincaExists() {
        when(fincaRepository.findById(1L)).thenReturn(Optional.of(finca));
        FincaResponseDTO result = fincaService.findById(1L);
        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    void findById_shouldThrowException_whenFincaNotFound() {
        when(fincaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> fincaService.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    @Test
    void create_shouldSaveAndReturnDTO() {
        FincaRequestDTO dto = new FincaRequestDTO("Nueva", "Juan", "San José", "Suaita", 5.0);
        when(fincaRepository.save(any(Finca.class))).thenReturn(finca);
        FincaResponseDTO result = fincaService.create(dto);
        assertThat(result).isNotNull();
        verify(fincaRepository, times(1)).save(any(Finca.class));
    }

    @Test
    void delete_shouldThrowException_whenNotExists() {
        when(fincaRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> fincaService.delete(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }
}
