package co.sena.adso.fincasapi.service;

import co.sena.adso.fincasapi.dto.ProductoRequestDTO;
import co.sena.adso.fincasapi.dto.ProductoResponseDTO;
import co.sena.adso.fincasapi.entity.Categoria;
import co.sena.adso.fincasapi.entity.Producto;
import co.sena.adso.fincasapi.exception.ResourceNotFoundException;
import co.sena.adso.fincasapi.repository.CategoriaRepository;
import co.sena.adso.fincasapi.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findAll() {
        return productoRepository.findAll().stream()
            .map(ProductoResponseDTO::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO findById(@org.springframework.lang.NonNull Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        return ProductoResponseDTO.fromEntity(producto);
    }

    @Transactional
    public ProductoResponseDTO create(ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoria", dto.categoriaId()));
        Producto producto = new Producto(dto.nombre(), dto.precio(), dto.stock(), categoria);
        return ProductoResponseDTO.fromEntity(productoRepository.save(producto));
    }

    @Transactional
    public ProductoResponseDTO update(@org.springframework.lang.NonNull Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoria", dto.categoriaId()));
        producto.setNombre(dto.nombre());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
        producto.setCategoria(categoria);
        return ProductoResponseDTO.fromEntity(productoRepository.save(producto));
    }

    @Transactional
    public void delete(@org.springframework.lang.NonNull Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", id);
        }
        productoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findByCategoriaId(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId).stream()
            .map(ProductoResponseDTO::fromEntity)
            .toList();
    }
}
