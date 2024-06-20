package it.inail.geodnotifapp.security.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TokenDetails implements Serializable {

    private static final long serialVersionUID = 3078754234898210883L;

    private String subject;

    private String sid;

    private Date expirationTime;

    private List<String> userGroups;

    private List<String> authorities;
	
    private String selectedRole;

    private String token;

    public static TokenDetails.TokenDetailsBuilder builder() {
        return new TokenDetails.TokenDetailsBuilder();
    }

    public TokenDetails(String subject, String sid, Date expirationTime, List<String> userGroups,List<String> authorities,
                        String token, String selectedRole) {
        this.subject = subject;
        this.sid = sid;
        this.expirationTime = expirationTime;
        this.userGroups = userGroups;
        this.authorities = authorities;
        this.token = token;
        this.selectedRole = selectedRole;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public List<String> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<String> userGroups) {
        this.userGroups = userGroups;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    public static class TokenDetailsBuilder {
        private String subject;
        private String sid;
        private Date expirationTime;
        private List<String> userGroups;
        private List<String> authorities;
        private String token;
        private String selectedRole;

        TokenDetailsBuilder() {
        }

        public TokenDetails.TokenDetailsBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public TokenDetails.TokenDetailsBuilder sid(String sid) {
            this.sid = sid;
            return this;
        }

        public TokenDetails.TokenDetailsBuilder expirationTime(Date expirationTime) {
            this.expirationTime = expirationTime;
            return this;
        }

        public TokenDetails.TokenDetailsBuilder userGroups(List<String> userGroups) {
            this.userGroups = userGroups;
            return this;
        }
		
        public TokenDetails.TokenDetailsBuilder authorities(List<String> authorities) {
            this.authorities = authorities;
            return this;
        }
        
		public TokenDetails.TokenDetailsBuilder token(String token) {
            this.token = token;
            return this;
        }
		
        public TokenDetails.TokenDetailsBuilder selectedRole(String selectedRole) {
            this.selectedRole = selectedRole;
            return this;
        }
		
        public TokenDetails build() {
            return new TokenDetails(this.subject, this.sid, this.expirationTime, this.userGroups, this.authorities,
                    this.token, this.selectedRole);
        }

        public String toString() {
            return "TokenDetails.TokenDetailsBuilder(subject=" + this.subject +
                    ", sid=" + this.sid + ", " + "expirationTime=" + this.expirationTime +
                    ", userGroups=" + this.userGroups + ", " + "authorities=" + this.authorities +
                    ", selectedRole="+ this.selectedRole + ", " + "token=" + this.token + ")" ;
        }
    }
}
