package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.CategoriaRequestDTO;
import co.sena.adso.fincasapi.dto.CategoriaResponseDTO;
import co.sena.adso.fincasapi.entity.Categoria;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> findAll() {
        return categoriaRepository.findAll().stream()
            .map(CategoriaResponseDTO::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO findById(@org.springframework.lang.NonNull Long id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
        return CategoriaResponseDTO.fromEntity(categoria);
    }

    @Transactional
    public CategoriaResponseDTO create(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria(dto.nombre(), dto.descripcion());
        return CategoriaResponseDTO.fromEntity(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponseDTO update(@org.springframework.lang.NonNull Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());
        return CategoriaResponseDTO.fromEntity(categoriaRepository.save(categoria));
    }

    @Transactional
    public void delete(@org.springframework.lang.NonNull Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria", id);
        }
        categoriaRepository.deleteById(id);
    }
}
