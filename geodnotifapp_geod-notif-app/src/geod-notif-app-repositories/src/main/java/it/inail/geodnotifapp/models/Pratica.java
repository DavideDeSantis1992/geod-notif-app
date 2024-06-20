package it.inail.geodnotifapp.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The Class Pratica.
 */
@Entity
@Table(name = "PRATICHE")
public class Pratica {

	/**
	 * The id.
	 */
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "PRATICHE_SEQUENCE", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRATICHE_SEQUENCE")
	private Long id;

	/**
	 * The name.
	 */
	@Column(name = "pratica_name")
	private String name;

	/**
	 * The description.
	 */
	@Column(name = "pratica_desc")
	private String description;

	/**
	 * The creation date.
	 */
	@Column(name = "pratica_create_date")
	private Date creationDate;

	/**
	 * The creator.
	 */
	@Column(name = "pratica_creator")
	private String creator;

	/**
	 * The modified at.
	 */
	@Column(name = "pratica_modify_date")
	private Date modifiedAt;

	/**
	 * The modifier.
	 */
	@Column(name = "pratica_modifier")
	private String modifier;

	/**
	 * Instantiates a new pratica.
	 */
	public Pratica() {
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Gets the creator.
	 *
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Sets the creator.
	 *
	 * @param creator the new creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * Instantiates a new pratica.
	 *
	 * @param id the id
	 * @param name the name
	 * @param description the description
	 * @param creationDate the creation date
	 * @param creator the creator
	 * @param modifiedAt the modified at
	 * @param modifier the modifier
	 */
	public Pratica(Long id, String name, String description, Date creationDate, String creator, Date modifiedAt,
			String modifier) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.creationDate = creationDate;
		this.creator = creator;
		this.modifiedAt = modifiedAt;
		this.modifier = modifier;
	}

}
