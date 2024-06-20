package it.inail.geodnotifapp.dto;

import java.io.Serializable;

/**
 * DTO servizio di INAIL
 */
public class AnagraficaDipendente implements Serializable{
  
  /**
   * The Constant serialVersionUID.
   */
  private static final long serialVersionUID = -1485180129600192496L;
  
  /**
   * The matricola.
   */
  private String matricola;
  
  /**
   * The codice fiscale.
   */
  private String codiceFiscale;
  
  /**
   * The cognome.
   */
  private String cognome;
  
  /**
   * The nome.
   */
  private String nome;
  
  /**
   * The contratto.
   */
  private String contratto;
  
  /**
   * The livello.
   */
  private String livello;
  
  /**
   * The qualifica.
   */
  private String qualifica;
  
  /**
   * The pec.
   */
  private String pec;
  
  /**
   * The numero cellulare.
   */
  private String numeroCellulare;
  
  /**
   * The numero cellulare privato.
   */
  private String numeroCellularePrivato;
  
  /**
   * The flag visibilita cellulare.
   */
  private String flagVisibilitaCellulare;
  
  /**
   * The numero breve.
   */
  private String numeroBreve;
  
  /**
   * The mail.
   */
  private String mail;
  
  /**
   * The net fax.
   */
  private String netFax;
  
  /**
   * The utente hr.
   */
  private String utenteHr;
  
  /**
   * The numero telefono.
   */
  private String numeroTelefono;
  
  /**
   * The codice unita.
   */
  private String codiceUnita;
  
  /**
   * The descrizione unita.
   */
  private String descrizioneUnita;

  /**
   * Instantiates a new anagrafica dipendente.
   */
  public AnagraficaDipendente() {
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

  /**
   * Gets the codice fiscale.
   *
   * @return the codice fiscale
   */
  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  /**
   * Sets the codice fiscale.
   *
   * @param codiceFiscale the new codice fiscale
   */
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  /**
   * Gets the cognome.
   *
   * @return the cognome
   */
  public String getCognome() {
    return cognome;
  }

  /**
   * Sets the cognome.
   *
   * @param cognome the new cognome
   */
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   * Gets the nome.
   *
   * @return the nome
   */
  public String getNome() {
    return nome;
  }

  /**
   * Sets the nome.
   *
   * @param nome the new nome
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * Gets the contratto.
   *
   * @return the contratto
   */
  public String getContratto() {
    return contratto;
  }

  /**
   * Sets the contratto.
   *
   * @param contratto the new contratto
   */
  public void setContratto(String contratto) {
    this.contratto = contratto;
  }

  /**
   * Gets the livello.
   *
   * @return the livello
   */
  public String getLivello() {
    return livello;
  }

  /**
   * Sets the livello.
   *
   * @param livello the new livello
   */
  public void setLivello(String livello) {
    this.livello = livello;
  }

  /**
   * Gets the qualifica.
   *
   * @return the qualifica
   */
  public String getQualifica() {
    return qualifica;
  }

  /**
   * Sets the qualifica.
   *
   * @param qualifica the new qualifica
   */
  public void setQualifica(String qualifica) {
    this.qualifica = qualifica;
  }

  /**
   * Gets the pec.
   *
   * @return the pec
   */
  public String getPec() {
    return pec;
  }

  /**
   * Sets the pec.
   *
   * @param pec the new pec
   */
  public void setPec(String pec) {
    this.pec = pec;
  }

  /**
   * Gets the numero cellulare.
   *
   * @return the numero cellulare
   */
  public String getNumeroCellulare() {
    return numeroCellulare;
  }

  /**
   * Sets the numero cellulare.
   *
   * @param numeroCellulare the new numero cellulare
   */
  public void setNumeroCellulare(String numeroCellulare) {
    this.numeroCellulare = numeroCellulare;
  }

  /**
   * Gets the numero cellulare privato.
   *
   * @return the numero cellulare privato
   */
  public String getNumeroCellularePrivato() {
    return numeroCellularePrivato;
  }

  /**
   * Sets the numero cellulare privato.
   *
   * @param numeroCellularePrivato the new numero cellulare privato
   */
  public void setNumeroCellularePrivato(String numeroCellularePrivato) {
    this.numeroCellularePrivato = numeroCellularePrivato;
  }

  /**
   * Gets the flag visibilita cellulare.
   *
   * @return the flag visibilita cellulare
   */
  public String getFlagVisibilitaCellulare() {
    return flagVisibilitaCellulare;
  }

  /**
   * Sets the flag visibilita cellulare.
   *
   * @param flagVisibilitaCellulare the new flag visibilita cellulare
   */
  public void setFlagVisibilitaCellulare(String flagVisibilitaCellulare) {
    this.flagVisibilitaCellulare = flagVisibilitaCellulare;
  }

  /**
   * Gets the numero breve.
   *
   * @return the numero breve
   */
  public String getNumeroBreve() {
    return numeroBreve;
  }

  /**
   * Sets the numero breve.
   *
   * @param numeroBreve the new numero breve
   */
  public void setNumeroBreve(String numeroBreve) {
    this.numeroBreve = numeroBreve;
  }

  /**
   * Gets the mail.
   *
   * @return the mail
   */
  public String getMail() {
    return mail;
  }

  /**
   * Sets the mail.
   *
   * @param mail the new mail
   */
  public void setMail(String mail) {
    this.mail = mail;
  }

  /**
   * Gets the net fax.
   *
   * @return the net fax
   */
  public String getNetFax() {
    return netFax;
  }

  /**
   * Sets the net fax.
   *
   * @param netFax the new net fax
   */
  public void setNetFax(String netFax) {
    this.netFax = netFax;
  }

  /**
   * Gets the utente hr.
   *
   * @return the utente hr
   */
  public String getUtenteHr() {
    return utenteHr;
  }

  /**
   * Sets the utente hr.
   *
   * @param utenteHr the new utente hr
   */
  public void setUtenteHr(String utenteHr) {
    this.utenteHr = utenteHr;
  }

  /**
   * Gets the numero telefono.
   *
   * @return the numero telefono
   */
  public String getNumeroTelefono() {
    return numeroTelefono;
  }

  /**
   * Sets the numero telefono.
   *
   * @param numeroTelefono the new numero telefono
   */
  public void setNumeroTelefono(String numeroTelefono) {
    this.numeroTelefono = numeroTelefono;
  }

  /**
   * Gets the codice unita.
   *
   * @return the codice unita
   */
  public String getCodiceUnita() {
    return codiceUnita;
  }

  /**
   * Sets the codice unita.
   *
   * @param codiceUnita the new codice unita
   */
  public void setCodiceUnita(String codiceUnita) {
    this.codiceUnita = codiceUnita;
  }

  /**
   * Gets the descrizione unita.
   *
   * @return the descrizione unita
   */
  public String getDescrizioneUnita() {
    return descrizioneUnita;
  }

  /**
   * Sets the descrizione unita.
   *
   * @param descrizioneUnita the new descrizione unita
   */
  public void setDescrizioneUnita(String descrizioneUnita) {
    this.descrizioneUnita = descrizioneUnita;
  }
}
