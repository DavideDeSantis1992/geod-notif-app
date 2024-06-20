package it.inail.geodnotifapp.security.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class JWTResponse.
 */
public class JWTResponse implements Serializable{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 3517883440446266311L;

	/**
	 * The authorization.
	 */
	@JsonProperty("Authorization")
    private String authorization;

    /**
     * Instantiates a new JWT response.
     */
    public JWTResponse() {
		super();
	}

	/**
	 * Gets the authorization.
	 *
	 * @return the authorization
	 */
	public String getAuthorization() {
        return authorization;
    }

    /**
     * Sets the authorization.
     *
     * @param authorization the new authorization
     */
    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
