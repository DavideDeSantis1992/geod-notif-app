package it.inail.geodnotifapp.security.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class GetAttributiResponse.
 */
public class GetAttributiResponse implements Serializable{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 6683701384867292773L;

	/**
	 * The codice.
	 */
	private String codice;

    /**
     * The istanze utente.
     */
    List<IstanzeUtente> istanzeUtente;

    /**
     * Instantiates a new gets the attributi response.
     */
    public GetAttributiResponse() {
		super();
	}

	/**
	 * Gets the codice.
	 *
	 * @return the codice
	 */
	public String getCodice() {
        return codice;
    }

    /**
     * Sets the codice.
     *
     * @param codice the new codice
     */
    public void setCodice(String codice) {
        this.codice = codice;
    }

    /**
     * Gets the istanze utente.
     *
     * @return the istanze utente
     */
    public List<IstanzeUtente> getIstanzeUtente() {
        return istanzeUtente;
    }

    /**
     * Sets the istanze utente.
     *
     * @param istanzeUtente the new istanze utente
     */
    public void setIstanzeUtente(List<IstanzeUtente> istanzeUtente) {
        this.istanzeUtente = istanzeUtente;
    }
}
