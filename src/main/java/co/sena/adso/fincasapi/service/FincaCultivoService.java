package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.*;
import co.sena.adso.fincasapi.entity.*;
import co.sena.adso.fincasapi.exception.BusinessException;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FincaCultivoService {

    private final FincaCultivoRepository fcRepository;
    private final FincaRepository fincaRepository;
    private final CultivoRepository cultivoRepository;

    public FincaCultivoService(FincaCultivoRepository fcRepository, FincaRepository fincaRepository, CultivoRepository cultivoRepository) {
        this.fcRepository = fcRepository;
        this.fincaRepository = fincaRepository;
        this.cultivoRepository = cultivoRepository;
    }

    @Transactional
    public FincaCultivoResponseDTO create(FincaCultivoRequestDTO dto) {
        Finca finca = fincaRepository.findById(dto.fincaId())
            .orElseThrow(() -> new ResourceNotFoundException("Finca", dto.fincaId()));
        Cultivo cultivo = cultivoRepository.findById(dto.cultivoId())
            .orElseThrow(() -> new ResourceNotFoundException("Cultivo", dto.cultivoId()));

        if (dto.areaSembradaHa() > finca.getHectareas()) {
            throw new BusinessException("El área sembrada (" + dto.areaSembradaHa() + " Ha) supera el tamaño disponible de la finca (" + finca.getHectareas() + " Ha).");
        }

        FincaCultivo fc = new FincaCultivo(finca, cultivo, dto.areaSembradaHa(), dto.fechaSiembra(), dto.temporada(), dto.estado());
        return FincaCultivoResponseDTO.fromEntity(fcRepository.save(fc));
    }

    @Transactional(readOnly = true)
    public List<FincaCultivoResponseDTO> findAll() {
        return fcRepository.findAll().stream()
            .map(FincaCultivoResponseDTO::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public FincaCultivoResponseDTO findById(@org.springframework.lang.NonNull Long id) {
        FincaCultivo fc = fcRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("FincaCultivo", id));
        return FincaCultivoResponseDTO.fromEntity(fc);
    }

    @Transactional(readOnly = true)
    public List<FincaCultivoResponseDTO> findByFincaId(@org.springframework.lang.NonNull Long fincaId) {
        return fcRepository.findByFincaId(fincaId).stream()
            .map(FincaCultivoResponseDTO::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<FincaCultivoResponseDTO> findByCultivoId(@org.springframework.lang.NonNull Long cultivoId) {
        return fcRepository.findByCultivoIdOrderByAreaSembradaHaDesc(cultivoId).stream()
            .map(FincaCultivoResponseDTO::fromEntity)
            .toList();
    }

    @Transactional
    public FincaCultivoResponseDTO update(@org.springframework.lang.NonNull Long id, FincaCultivoRequestDTO dto) {
        FincaCultivo fc = fcRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("FincaCultivo", id));
        Finca finca = fincaRepository.findById(dto.fincaId())
            .orElseThrow(() -> new ResourceNotFoundException("Finca", dto.fincaId()));
        Cultivo cultivo = cultivoRepository.findById(dto.cultivoId())
            .orElseThrow(() -> new ResourceNotFoundException("Cultivo", dto.cultivoId()));

        if (dto.areaSembradaHa() > finca.getHectareas()) {
            throw new BusinessException("El área sembrada (" + dto.areaSembradaHa() + " Ha) supera el tamaño disponible de la finca (" + finca.getHectareas() + " Ha).");
        }

        fc.setFinca(finca);
        fc.setCultivo(cultivo);
        fc.setAreaSembradaHa(dto.areaSembradaHa());
        fc.setFechaSiembra(dto.fechaSiembra());
        fc.setTemporada(dto.temporada());
        fc.setEstado(dto.estado());
        return FincaCultivoResponseDTO.fromEntity(fcRepository.save(fc));
    }

    @Transactional
    public void delete(@org.springframework.lang.NonNull Long id) {
        if (!fcRepository.existsById(id)) {
            throw new ResourceNotFoundException("FincaCultivo", id);
        }
        fcRepository.deleteById(id);
    }
}
