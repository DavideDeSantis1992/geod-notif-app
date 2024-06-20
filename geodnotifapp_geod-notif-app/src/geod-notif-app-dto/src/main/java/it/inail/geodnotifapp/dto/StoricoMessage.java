package it.inail.geodnotifapp.dto;

import java.io.Serializable;

/**
 * The Class StoricoMessage.
 */
public class StoricoMessage implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 6007561705323568463L;

	/**
	 * The pratica id.
	 */
	private Long praticaId;

	/**
	 * The description.
	 */
	private String description;

	/**
	 * The username.
	 */
	private String username;

	/**
	 * Instantiates a new storico message.
	 *
	 * @param praticaId the pratica id
	 * @param description the description
	 * @param username the username
	 */
	public StoricoMessage(Long praticaId, String description, String username) {
		this.praticaId = praticaId;
		this.description = description;
		this.username = username;
	}

	/**
	 * Gets the pratica id.
	 *
	 * @return the pratica id
	 */
	public Long getPraticaId() {
		return praticaId;
	}

	/**
	 * Sets the pratica id.
	 *
	 * @param praticaId the new pratica id
	 */
	public void setPraticaId(Long praticaId) {
		this.praticaId = praticaId;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
