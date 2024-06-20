package it.inail.geodnotifapp.dto;

import java.util.List;

/**
 * The Class AnagraficaDipedentiResponse.
 */
public class AnagraficaDipedentiResponse {

  /**
   * The dipendente.
   */
  private List<AnagraficaDipendente> dipendente;

  /**
   * Instantiates a new anagrafica dipedenti response.
   */
  public AnagraficaDipedentiResponse() {
	  super();
  }
  
  /**
   * Gets the dipendente.
   *
   * @return the dipendente
   */
  public List<AnagraficaDipendente> getDipendente() {
    return dipendente;
  }

  /**
   * Sets the dipendente.
   *
   * @param dipendente the new dipendente
   */
  public void setDipendente(List<AnagraficaDipendente> dipendente) {
    this.dipendente = dipendente;
  }
}
