package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.*;
import co.sena.adso.fincasapi.entity.Finca;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.FincaRepository;
import co.sena.adso.fincasapi.specification.FincaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * La capa Service (Servicio) contiene la Lógica de Negocio.
 * Aquí es donde aplicamos las reglas, cálculos y orquestamos el acceso a datos.
 * El Controlador le pide cosas al Servicio, y el Servicio le pide cosas al Repositorio.
 * 
 * @Service: Indica a Spring que esta clase es un servicio. Spring la instanciará
 *           una sola vez (patrón Singleton) y la tendrá lista para inyectarla.
 */
@Service
public class FincaService {

    // Dependencia del repositorio para conectarnos a la base de datos
    private final FincaRepository fincaRepository;

    public FincaService(FincaRepository fincaRepository) {
        this.fincaRepository = fincaRepository;
    }

    /**
     * @Transactional(readOnly = true) le dice a la base de datos que esta operación
     * es SOLO de lectura. Esto mejora el rendimiento porque Spring no tiene que 
     * llevar un registro de los cambios en los objetos.
     */
    @Transactional(readOnly = true)
    public List<FincaResponseDTO> findAll() {
        return fincaRepository.findAll().stream()
            .map(FincaResponseDTO::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public FincaResponseDTO findById(@org.springframework.lang.NonNull Long id) {
        Finca finca = fincaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Finca", id));
        return FincaResponseDTO.fromEntity(finca);
    }

    /**
     * @Transactional (sin readOnly) envuelve el método en una transacción de base de datos.
     * Si ocurre un error a mitad de camino, todos los cambios se deshacen (rollback).
     */
    @Transactional
    public FincaResponseDTO create(FincaRequestDTO dto) {
        Finca finca = new Finca(dto.nombre(), dto.propietario(), dto.vereda(), dto.municipio(), dto.hectareas());
        return FincaResponseDTO.fromEntity(fincaRepository.save(finca));
    }

    @Transactional
    public FincaResponseDTO update(@org.springframework.lang.NonNull Long id, FincaRequestDTO dto) {
        Finca finca = fincaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Finca", id));
        finca.setNombre(dto.nombre());
        finca.setPropietario(dto.propietario());
        finca.setVereda(dto.vereda());
        finca.setMunicipio(dto.municipio());
        finca.setHectareas(dto.hectareas());
        return FincaResponseDTO.fromEntity(fincaRepository.save(finca));
    }

    @Transactional
    public void delete(@org.springframework.lang.NonNull Long id) {
        if (!fincaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Finca", id);
        }
        fincaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<FincaResponseDTO> findAllPaginado(@org.springframework.lang.NonNull Pageable pageable) {
        return fincaRepository.findAll(pageable)
            .map(FincaResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<FincaResponseDTO> buscar(String municipio, String propietario, Double hectareasMin) {
        return fincaRepository.findAll(FincaSpecification.withFilters(municipio, propietario, hectareasMin, null))
            .stream()
            .map(FincaResponseDTO::fromEntity)
            .toList();
    }
}
