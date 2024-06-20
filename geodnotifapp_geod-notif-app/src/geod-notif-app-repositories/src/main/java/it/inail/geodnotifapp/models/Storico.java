package it.inail.geodnotifapp.models;

import javax.persistence.*;
import java.util.Date;

/**
 * The Class Storico.
 */
@Entity
@Table(name = "STORICO")
public class Storico {

	/**
	 * The id.
	 */
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "STORICO_SEQUENCE", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORICO_SEQUENCE")
	private Long id;

	/**
	 * The pratica id.
	 */
	@Column(name = "log_pratica_id")
	private Long praticaId;

	/**
	 * The description.
	 */
	@Column(name = "log_desc")
	private String description;

	/**
	 * The creation date.
	 */
	@Column(name = "log_date")
	private Date creationDate;

	/**
	 * The username.
	 */
	@Column(name = "username")
	private String username;

	/**
	 * Instantiates a new storico.
	 */
	public Storico() {
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
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
