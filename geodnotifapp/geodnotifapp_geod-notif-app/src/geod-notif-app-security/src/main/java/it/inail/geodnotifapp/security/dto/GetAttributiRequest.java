package it.inail.geodnotifapp.security.dto;

import java.io.Serializable;

/**
 * The Class GetAttributiRequest.
 */
public class GetAttributiRequest implements Serializable{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -6731150621830873634L;

	/**
	 * The codicegruppo.
	 */
	private String codicegruppo;

    /**
     * Instantiates a new gets the attributi request.
     */
    public GetAttributiRequest() {
    }

    /**
     * Instantiates a new gets the attributi request.
     *
     * @param codicegruppo the codicegruppo
     */
    public GetAttributiRequest(String codicegruppo) {
        this.codicegruppo = codicegruppo;
    }

    /**
     * Gets the codicegruppo.
     *
     * @return the codicegruppo
     */
    public String getCodicegruppo() {
        return codicegruppo;
    }

    /**
     * Sets the codicegruppo.
     *
     * @param codicegruppo the new codicegruppo
     */
    public void setCodicegruppo(String codicegruppo) {
        this.codicegruppo = codicegruppo;
    }
}
