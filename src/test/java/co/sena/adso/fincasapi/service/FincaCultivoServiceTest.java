package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.FincaCultivoRequestDTO;
import co.sena.adso.fincasapi.dto.FincaCultivoResponseDTO;
import co.sena.adso.fincasapi.entity.Cultivo;
import co.sena.adso.fincasapi.entity.Finca;
import co.sena.adso.fincasapi.entity.FincaCultivo;
import co.sena.adso.fincasapi.enums.Estado;
import co.sena.adso.fincasapi.enums.Temporada;
import co.sena.adso.fincasapi.exception.BusinessException;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.CultivoRepository;
import co.sena.adso.fincasapi.repository.FincaCultivoRepository;
import co.sena.adso.fincasapi.repository.FincaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
class FincaCultivoServiceTest {

    @Mock
    private FincaCultivoRepository fcRepository;

    @Mock
    private FincaRepository fincaRepository;

    @Mock
    private CultivoRepository cultivoRepository;

    @InjectMocks
    private FincaCultivoService fcService;

    private Finca finca;
    private Cultivo cultivo;
    private FincaCultivo fc;

    @BeforeEach
    void setUp() {
        finca = new Finca("La Esperanza", "Carlos", "El Gualilo", "Vélez", 12.5);
        finca.setId(1L);
        cultivo = new Cultivo("Café", "permanente", 365);
        cultivo.setId(1L);
        fc = new FincaCultivo(finca, cultivo, 5.0, LocalDate.of(2026, 3, 15), Temporada.PRIMAVERA, Estado.ACTIVO);
        fc.setId(1L);
    }

    @Test
    void findAll_shouldReturnListOfDTOs() {
        when(fcRepository.findAll()).thenReturn(List.of(fc));
        List<FincaCultivoResponseDTO> result = fcService.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void findById_shouldReturnDTO_whenExists() {
        when(fcRepository.findById(1L)).thenReturn(Optional.of(fc));
        FincaCultivoResponseDTO result = fcService.findById(1L);
        assertThat(result.id()).isEqualTo(1L);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(fcRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> fcService.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_shouldSaveAndReturnDTO() {
        FincaCultivoRequestDTO dto = new FincaCultivoRequestDTO(1L, 1L, 5.0, LocalDate.of(2026, 3, 15), Temporada.PRIMAVERA, Estado.ACTIVO);
        when(fincaRepository.findById(1L)).thenReturn(Optional.of(finca));
        when(cultivoRepository.findById(1L)).thenReturn(Optional.of(cultivo));
        when(fcRepository.save(any(FincaCultivo.class))).thenReturn(fc);
        FincaCultivoResponseDTO result = fcService.create(dto);
        assertThat(result).isNotNull();
        verify(fcRepository, times(1)).save(any(FincaCultivo.class));
    }

    @Test
    void create_shouldThrowBusinessException_whenAreaExceedsFinca() {
        FincaCultivoRequestDTO dto = new FincaCultivoRequestDTO(1L, 1L, 20.0, LocalDate.of(2026, 3, 15), Temporada.PRIMAVERA, Estado.ACTIVO);
        when(fincaRepository.findById(1L)).thenReturn(Optional.of(finca));
        when(cultivoRepository.findById(1L)).thenReturn(Optional.of(cultivo));
        assertThatThrownBy(() -> fcService.create(dto))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("supera el tamaño");
    }

    @Test
    void create_shouldThrowException_whenFincaNotFound() {
        FincaCultivoRequestDTO dto = new FincaCultivoRequestDTO(99L, 1L, 5.0, LocalDate.of(2026, 3, 15), Temporada.PRIMAVERA, Estado.ACTIVO);
        when(fincaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> fcService.create(dto))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void findByFincaId_shouldReturnList() {
        when(fcRepository.findByFincaId(1L)).thenReturn(List.of(fc));
        List<FincaCultivoResponseDTO> result = fcService.findByFincaId(1L);
        assertThat(result).hasSize(1);
    }

    @Test
    void delete_shouldThrowException_whenNotExists() {
        when(fcRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> fcService.delete(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }
}
