package it.inail.geodnotifapp.mappers;

import it.inail.geodnotifapp.dto.StoricoDto;
import it.inail.geodnotifapp.models.Storico;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-21T09:52:01+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.23 (Amazon.com Inc.)"
)
@Component
public class StoricoMapperImpl implements StoricoMapper {

    @Override
    public StoricoDto map(Storico entity) {
        if ( entity == null ) {
            return null;
        }

        StoricoDto storicoDto = new StoricoDto();

        if ( entity.getId() != null ) {
            storicoDto.setId( entity.getId() );
        }
        storicoDto.setDescription( entity.getDescription() );
        storicoDto.setUsername( entity.getUsername() );
        storicoDto.setDate( entity.getCreationDate() );

        return storicoDto;
    }

    @Override
    public List<StoricoDto> map(List<Storico> praticaList) {
        if ( praticaList == null ) {
            return null;
        }

        List<StoricoDto> list = new ArrayList<StoricoDto>( praticaList.size() );
        for ( Storico storico : praticaList ) {
            list.add( map( storico ) );
        }

        return list;
    }
}
