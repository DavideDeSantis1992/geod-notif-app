package it.inail.geodnotifapp.mappers;

import it.inail.geodnotifapp.dto.StoricoDto;
import it.inail.geodnotifapp.models.Storico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoricoMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "creationDate", target = "date")
    })
    StoricoDto map(Storico entity);

    List<StoricoDto> map(List<Storico> praticaList);
}
