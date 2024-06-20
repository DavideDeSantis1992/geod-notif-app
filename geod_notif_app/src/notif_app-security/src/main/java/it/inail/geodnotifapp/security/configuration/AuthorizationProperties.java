package it.inail.geodnotifapp.security.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "inail.security")
public class AuthorizationProperties {

	private Boolean enableSecurity = Boolean.TRUE;

	private Boolean checkHeadOffice = Boolean.TRUE;

	private Boolean checkUserRole = Boolean.TRUE;

	private Boolean loadTechnicalRoles = Boolean.FALSE;

	@NotEmpty
	private List<String> validAudience;

	@NotEmpty
	private String clientId;

	@NotEmpty
	private String accessSecret;

	@NotEmpty
	private String createJWTServiceUrl;

	@NotEmpty
	private String getAttributiBEUrl;

	@NotEmpty
	private String wellKnownUrl;

	public AuthorizationProperties() {
		super();
	}

	public Boolean getEnableSecurity() {
		return enableSecurity;
	}

	public void setEnableSecurity(Boolean enableSecurity) {
		this.enableSecurity = enableSecurity;
	}

	public Boolean getCheckHeadOffice() {
		return checkHeadOffice;
	}

	public void setCheckHeadOffice(Boolean checkHeadOffice) {
		this.checkHeadOffice = checkHeadOffice;
	}

	public Boolean getCheckUserRole() {
		return checkUserRole;
	}

	public void setCheckUserRole(Boolean checkUserRole) {
		this.checkUserRole = checkUserRole;
	}

	public Boolean getLoadTechnicalRoles() {
		return loadTechnicalRoles;
	}

	public void setLoadTechnicalRoles(Boolean loadTechnicalRoles) {
		this.loadTechnicalRoles = loadTechnicalRoles;
	}

	public List<String> getValidAudience() {
		return validAudience;
	}

	public void setValidAudience(List<String> validAudience) {
		this.validAudience = validAudience;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAccessSecret() {
		return accessSecret;
	}

	public void setAccessSecret(String accessSecret) {
		this.accessSecret = accessSecret;
	}

	public String getCreateJWTServiceUrl() {
		return createJWTServiceUrl;
	}

	public void setCreateJWTServiceUrl(String createJWTServiceUrl) {
		this.createJWTServiceUrl = createJWTServiceUrl;
	}

	public String getGetAttributiBEUrl() {
		return getAttributiBEUrl;
	}

	public void setGetAttributiBEUrl(String getAttributiBEUrl) {
		this.getAttributiBEUrl = getAttributiBEUrl;
	}

	public String getWellKnownUrl() {
		return wellKnownUrl;
	}

	public void setWellKnownUrl(String wellKnownUrl) {
		this.wellKnownUrl = wellKnownUrl;
	}
}
