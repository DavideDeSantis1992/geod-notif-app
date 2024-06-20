package it.inail.geodnotifapp.services;

import it.inail.geodnotifapp.dto.StoricoDto;

import java.util.List;

public interface StoricoService {

    void save(long praticaId, String message, String username);

    List<StoricoDto> getStorico(long praticaId);

}
