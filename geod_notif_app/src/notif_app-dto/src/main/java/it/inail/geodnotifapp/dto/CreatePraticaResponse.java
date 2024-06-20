package it.inail.geodnotifapp.dto;

import java.io.Serializable;

/**
 * The Class CreatePraticaResponse.
 */
public class CreatePraticaResponse implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -9074267824254547819L;
	
	/**
	 * The id.
	 */
	private Long id;
	
	/**
	 * Instantiates a new creates the pratica response.
	 */
	public CreatePraticaResponse() {
		super();
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
