package it.inail.geodnotifapp.services.impl;

import it.inail.geodnotifapp.dto.AnagraficaDipedentiResponse;
import it.inail.geodnotifapp.dto.AnagraficaDipendente;
import it.inail.geodnotifapp.external.AnagraficaDipendenteClient;
import it.inail.geodnotifapp.services.AnagraficaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnagraficaServiceImpl implements AnagraficaService {

  @Autowired
  AnagraficaDipendenteClient anagraficaDipendenteClient;

  @Override
  public AnagraficaDipendente searchDipendente(String matricolaDipendente) {

    AnagraficaDipedentiResponse response =
        anagraficaDipendenteClient.searchAnagrafiche(matricolaDipendente);

    return response.getDipendente().get(0);
  }
}