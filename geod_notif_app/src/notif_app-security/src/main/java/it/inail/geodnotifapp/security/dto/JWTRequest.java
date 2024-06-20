package it.inail.geodnotifapp.security.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class JWTRequest.
 */
public class JWTRequest implements Serializable{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -8699941685609537613L;

	/**
	 * The username.
	 */
	@JsonProperty("username")
    private String username;

    /**
     * Instantiates a new JWT request.
     */
    public JWTRequest() {
		super();
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
