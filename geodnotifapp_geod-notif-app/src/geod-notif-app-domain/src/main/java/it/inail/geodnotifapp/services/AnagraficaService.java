package it.inail.geodnotifapp.services;

import it.inail.geodnotifapp.dto.AnagraficaDipendente;

public interface AnagraficaService {
  public AnagraficaDipendente searchDipendente(String matricola);
}
