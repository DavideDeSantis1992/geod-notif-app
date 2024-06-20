package it.inail.geodnotifapp.services;

import it.inail.geodnotifapp.dto.CreatePraticaResponse;
import it.inail.geodnotifapp.dto.PraticaDto;

public interface PraticaService {

    CreatePraticaResponse create(PraticaDto praticaDto, String username);

    boolean update(Long id, PraticaDto praticaDto, String username);

    void delete(Long id, String username);

    PraticaDto find(Long id, String username);
}
