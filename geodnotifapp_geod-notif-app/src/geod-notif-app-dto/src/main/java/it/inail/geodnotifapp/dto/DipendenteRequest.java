package it.inail.geodnotifapp.dto;

import java.io.Serializable;

/**
 * The Class DipendenteRequest.
 */
public class DipendenteRequest  implements Serializable{

  /**
   * The Constant serialVersionUID.
   */
  private static final long serialVersionUID = 3704427553589279961L;

  /**
   * The matricola.
   */
  private String matricola;
  
  /**
   * Instantiates a new dipendente request.
   */
  public DipendenteRequest() {
	  super();
  }
  
  /**
   * Gets the matricola.
   *
   * @return the matricola
   */
  public String getMatricola() {
    return matricola;
  }

  /**
   * Sets the matricola.
   *
   * @param matricola the new matricola
   */
  public void setMatricola(String matricola) {
    this.matricola = matricola;
  }
}
