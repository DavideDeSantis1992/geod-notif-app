package it.inail.geodnotifapp.security.models;

import java.io.Serializable;

public class AuthDetails implements Serializable {

    private static final long serialVersionUID = -7253434658824117595L;

    public static final String PROFILE_SELECTED_KEY_SEP = "|";

    /** The sid. */
    private String sid;

    /** The csrf token. */
    private String csrfToken;

    /** The sede. */
    private String headOffice;

    /** The ruolo. */
    private String role;

    /** The office */
    private String office;

    /** The client id. */
    private String clientId;

    public AuthDetails() {
    }

    public AuthDetails(String sid, String csrfToken, String headOffice, String role, String office, String clientId) {
        this.sid = sid;
        this.csrfToken = csrfToken;
        this.headOffice = headOffice;
        this.role = role;
        this.office = office;
        this.clientId = clientId;
    }

    public String buildCurrentProfileData() {
        StringBuilder sb = new StringBuilder();
        sb.append(headOffice);
        sb.append(PROFILE_SELECTED_KEY_SEP);
        sb.append(role);
        if (office != null) {
            sb.append(PROFILE_SELECTED_KEY_SEP);
            sb.append(office);
        }
        return sb.toString();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    public String getHeadOffice() {
        return headOffice;
    }

    public void setHeadOffice(String headOffice) {
        this.headOffice = headOffice;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
