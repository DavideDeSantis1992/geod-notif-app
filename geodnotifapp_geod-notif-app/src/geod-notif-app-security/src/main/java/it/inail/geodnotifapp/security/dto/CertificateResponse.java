package it.inail.geodnotifapp.security.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CertificateResponse.
 */
public class CertificateResponse implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -28039199959804550L;

	/**
	 * The certificate JWT.
	 */
	@JsonProperty("CA_JWT")
	private String certificateJWT;

	/**
	 * The certificate token body.
	 */
	@JsonProperty("CA_AS")
	private String certificateTokenBody;

	/**
	 * The certificate backe end body.
	 */
	@JsonProperty("CA_BE")
	private String certificateBackeEndBody;

	/**
	 * Instantiates a new certificate response.
	 */
	public CertificateResponse() {
		super();
	}

	/**
	 * Gets the certificate JWT.
	 *
	 * @return the certificate JWT
	 */
	public String getCertificateJWT() {
		return certificateJWT;
	}

	/**
	 * Sets the certificate JWT.
	 *
	 * @param certificateJWT the new certificate JWT
	 */
	public void setCertificateJWT(String certificateJWT) {
		this.certificateJWT = certificateJWT;
	}

	/**
	 * Gets the certificate token body.
	 *
	 * @return the certificate token body
	 */
	public String getCertificateTokenBody() {
		return certificateTokenBody;
	}

	/**
	 * Sets the certificate token body.
	 *
	 * @param certificateTokenBody the new certificate token body
	 */
	public void setCertificateTokenBody(String certificateTokenBody) {
		this.certificateTokenBody = certificateTokenBody;
	}

	/**
	 * Gets the certificate backe end body.
	 *
	 * @return the certificate backe end body
	 */
	public String getCertificateBackeEndBody() {
		return certificateBackeEndBody;
	}

	/**
	 * Sets the certificate backe end body.
	 *
	 * @param certificateBackeEndBody the new certificate backe end body
	 */
	public void setCertificateBackeEndBody(String certificateBackeEndBody) {
		this.certificateBackeEndBody = certificateBackeEndBody;
	}
}
