package it.inail.geodnotifapp.security.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class IstanzeUtente.
 */
public class IstanzeUtente implements Serializable{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -8567226326361170504L;

	/**
	 * The id istanza.
	 */
	private Integer idIstanza;

    /**
     * The valori attributo.
     */
    private List<Attributo> valoriAttributo;

    /**
     * Instantiates a new istanze utente.
     */
    public IstanzeUtente() {
		super();
	}

	/**
	 * Gets the id istanza.
	 *
	 * @return the id istanza
	 */
	public Integer getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets the id istanza.
     *
     * @param idIstanza the new id istanza
     */
    public void setIdIstanza(Integer idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets the valori attributo.
     *
     * @return the valori attributo
     */
    public List<Attributo> getValoriAttributo() {
        return valoriAttributo;
    }

    /**
     * Sets the valori attributo.
     *
     * @param valoriAttributo the new valori attributo
     */
    public void setValoriAttributo(List<Attributo> valoriAttributo) {
        this.valoriAttributo = valoriAttributo;
    }
}
