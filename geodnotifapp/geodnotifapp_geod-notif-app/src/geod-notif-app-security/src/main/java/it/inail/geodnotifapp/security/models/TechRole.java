package it.inail.geodnotifapp.security.models;

import org.springframework.security.core.GrantedAuthority;

public class TechRole implements GrantedAuthority {

	private static final long serialVersionUID = 8428944930290061474L;
	private String id;
	private String description;

	public TechRole() {
	}

	@Override
	public String getAuthority() {
		return id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
