package co.sena.adso.fincasapi.mapper;

import co.sena.adso.fincasapi.dto.FincaRequestDTO;
import co.sena.adso.fincasapi.dto.FincaResponseDTO;
import co.sena.adso.fincasapi.entity.Finca;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class FincaMapper {

    public FincaResponseDTO toDTO(Finca finca) {
        return new FincaResponseDTO(
            finca.getId(),
            finca.getNombre(),
            finca.getPropietario(),
            finca.getVereda(),
            finca.getMunicipio(),
            finca.getHectareas()
        );
    }

    public Finca toEntity(FincaRequestDTO dto) {
        return new Finca(dto.nombre(), dto.propietario(), dto.vereda(), dto.municipio(), dto.hectareas());
    }

    public void updateEntity(Finca finca, FincaRequestDTO dto) {
        finca.setNombre(dto.nombre());
        finca.setPropietario(dto.propietario());
        finca.setVereda(dto.vereda());
        finca.setMunicipio(dto.municipio());
        finca.setHectareas(dto.hectareas());
    }

    public List<FincaResponseDTO> toDTOList(List<Finca> fincas) {
        return fincas.stream().map(this::toDTO).toList();
    }
}
