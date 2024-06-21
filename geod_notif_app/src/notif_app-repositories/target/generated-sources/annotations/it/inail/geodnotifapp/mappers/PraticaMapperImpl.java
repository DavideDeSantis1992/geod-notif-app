package it.inail.geodnotifapp.mappers;

import it.inail.geodnotifapp.dto.PraticaDto;
import it.inail.geodnotifapp.models.Pratica;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-21T09:52:02+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.23 (Amazon.com Inc.)"
)
@Component
public class PraticaMapperImpl implements PraticaMapper {

    @Override
    public PraticaDto map(Pratica pratica) {
        if ( pratica == null ) {
            return null;
        }

        PraticaDto praticaDto = new PraticaDto();

        praticaDto.setId( pratica.getId() );
        praticaDto.setName( pratica.getName() );
        praticaDto.setDescription( pratica.getDescription() );
        praticaDto.setAuthor( pratica.getCreator() );

        return praticaDto;
    }

    @Override
    public Pratica map(PraticaDto praticaDto) {
        if ( praticaDto == null ) {
            return null;
        }

        Pratica pratica = new Pratica();

        pratica.setId( praticaDto.getId() );
        pratica.setName( praticaDto.getName() );
        pratica.setDescription( praticaDto.getDescription() );
        pratica.setCreator( praticaDto.getAuthor() );

        return pratica;
    }

    @Override
    public List<PraticaDto> map(List<Pratica> praticaList) {
        if ( praticaList == null ) {
            return null;
        }

        List<PraticaDto> list = new ArrayList<PraticaDto>( praticaList.size() );
        for ( Pratica pratica : praticaList ) {
            list.add( map( pratica ) );
        }

        return list;
    }
}
