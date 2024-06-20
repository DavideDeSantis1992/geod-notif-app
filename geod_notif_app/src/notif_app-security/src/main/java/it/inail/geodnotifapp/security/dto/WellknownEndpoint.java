package it.inail.geodnotifapp.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * The Class WellknownEndpoint.
 */
public class WellknownEndpoint implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 7350947415071824677L;


    /**
     * The issuer.
     */
    @JsonProperty("issuer")
    private String issuer;

	/**
	 * The jwks url.
	 */
	@JsonProperty("jwks_uri")
    private String jwksUrl;

    /**
     * The jwks CA.
     */
    @JsonProperty("jwks_CA")
    private String jwksCA;

    /**
     * The b 2 b endpoint url.
     */
    @JsonProperty("Wellknownb2b_endpoint")
    private String b2bEndpointUrl;

    /**
     * Instantiates a new wellknown endpoint.
     */
    public WellknownEndpoint() {
		super();
	}

    /**
     * Gets the issuer.
     *
     * @return the issuer
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * Sets the issuer.
     *
     * @param issuer the new issuer
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

	/**
	 * Gets the jwks url.
	 *
	 * @return the jwks url
	 */
	public String getJwksUrl() {
        return jwksUrl;
    }

    /**
     * Sets the jwks url.
     *
     * @param jwksUrl the new jwks url
     */
    public void setJwksUrl(String jwksUrl) {
        this.jwksUrl = jwksUrl;
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

    /**
     * Gets the b 2 b endpoint url.
     *
     * @return the b 2 b endpoint url
     */
    public String getB2bEndpointUrl() {
        return b2bEndpointUrl;
    }

    /**
     * Sets the b 2 b endpoint url.
     *
     * @param b2bEndpointUrl the new b 2 b endpoint url
     */
    public void setB2bEndpointUrl(String b2bEndpointUrl) {
        this.b2bEndpointUrl = b2bEndpointUrl;
    }
}
