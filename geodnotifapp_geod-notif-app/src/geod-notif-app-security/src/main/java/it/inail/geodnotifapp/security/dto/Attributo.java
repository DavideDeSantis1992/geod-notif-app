package it.inail.geodnotifapp.security.dto;

import java.io.Serializable;

/**
 * The Class Attributo.
 */
public class Attributo implements Serializable{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -8539213272884946691L;

	/**
	 * The valore.
	 */
	private String valore;

    /**
     * The nome attributo.
     */
    private String nomeAttributo;

    /**
     * Instantiates a new attributo.
     */
    public Attributo() {
		super();
	}

	/**
	 * Gets the valore.
	 *
	 * @return the valore
	 */
	public String getValore() {
        return valore;
    }

    /**
     * Sets the valore.
     *
     * @param valore the new valore
     */
    public void setValore(String valore) {
        this.valore = valore;
    }

    /**
     * Gets the nome attributo.
     *
     * @return the nome attributo
     */
    public String getNomeAttributo() {
        return nomeAttributo;
    }

    /**
     * Sets the nome attributo.
     *
     * @param nomeAttributo the new nome attributo
     */
    public void setNomeAttributo(String nomeAttributo) {
        this.nomeAttributo = nomeAttributo;
    }
}
