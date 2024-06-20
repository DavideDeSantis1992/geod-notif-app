package it.inail.geodnotifapp.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * The Class B2BConfiguration.
 */
public class B2BConfiguration implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -8395609114821752171L;

	/**
	 * The token endpoint.
	 */
	@JsonProperty("token_endpoint")
    private String tokenEndpoint;

    /**
     * The jwks CA.
     */
    @JsonProperty("jwks_CA")
    private String jwksCA;

    /**
     * Instantiates a new b 2 B configuration.
     */
    public B2BConfiguration() {
		super();
	}

	/**
	 * Gets the token endpoint.
	 *
	 * @return the token endpoint
	 */
	public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    /**
     * Sets the token endpoint.
     *
     * @param tokenEndpoint the new token endpoint
     */
    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    /**
     * Gets the jwks CA.
     *
     * @return the jwks CA
     */
    public String getJwksCA() {
        return jwksCA;
    }

    /**
     * Sets the jwks CA.
     *
     * @param jwksCA the new jwks CA
     */
    public void setJwksCA(String jwksCA) {
        this.jwksCA = jwksCA;
    }
}
