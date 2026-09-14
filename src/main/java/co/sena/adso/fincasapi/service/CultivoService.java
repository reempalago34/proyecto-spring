package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.*;
import co.sena.adso.fincasapi.entity.Cultivo;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.CultivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CultivoService {

    private final CultivoRepository cultivoRepository;

    public CultivoService(CultivoRepository cultivoRepository) {
        this.cultivoRepository = cultivoRepository;
    }

    @Transactional(readOnly = true)
    public List<CultivoResponseDTO> findAll() {
        return cultivoRepository.findAll().stream()
            .map(CultivoResponseDTO::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public CultivoResponseDTO findById(@org.springframework.lang.NonNull Long id) {
        Cultivo cultivo = cultivoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cultivo", id));
        return CultivoResponseDTO.fromEntity(cultivo);
    }

    @Transactional
    public CultivoResponseDTO create(CultivoRequestDTO dto) {
        Cultivo cultivo = new Cultivo(dto.nombre(), dto.tipo(), dto.cicloDias());
        return CultivoResponseDTO.fromEntity(cultivoRepository.save(cultivo));
    }

    @Transactional
    public CultivoResponseDTO update(@org.springframework.lang.NonNull Long id, CultivoRequestDTO dto) {
        Cultivo cultivo = cultivoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cultivo", id));
        cultivo.setNombre(dto.nombre());
        cultivo.setTipo(dto.tipo());
        cultivo.setCicloDias(dto.cicloDias());
        return CultivoResponseDTO.fromEntity(cultivoRepository.save(cultivo));
    }

    @Transactional
    public void delete(@org.springframework.lang.NonNull Long id) {
        if (!cultivoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cultivo", id);
        }
        cultivoRepository.deleteById(id);
    }
}
