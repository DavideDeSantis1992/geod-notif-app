package it.inail.geodnotifapp.mappers;

import it.inail.geodnotifapp.dto.PraticaDto;
import it.inail.geodnotifapp.models.Pratica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PraticaMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "creator", target = "author")
    })
    PraticaDto map(Pratica pratica);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "author", target = "creator")
    })
    Pratica map(PraticaDto praticaDto);

    List<PraticaDto> map(List<Pratica> praticaList);
}
