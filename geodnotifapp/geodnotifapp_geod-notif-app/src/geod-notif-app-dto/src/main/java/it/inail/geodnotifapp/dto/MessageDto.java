package it.inail.geodnotifapp.dto;

import java.io.Serializable;

/**
 * The Class MessageDto.
 */
public class MessageDto implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 4040939271248256087L;

	/**
	 * The message.
	 */
	private String message;

	/**
	 * The author.
	 */
	private String author;

	/**
	 * The date.
	 */
	private String date;

	/**
	 * Instantiates a new message dto.
	 */
	public MessageDto() {
		super();
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(String date) {
		this.date = date;
	}
}
