package it.inail.geodnotifapp.security.models;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Classe che rappresenta l''oggetto Authentication del contesto di sicurezza e che viene iniettato nel controller.
 * Il bean rappresenta l''utente autenticato e contiene tutte le informazioni dell''utente stesso.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 2837177763727577721L;

    private TokenDetails tokenDetails;

    private AuthDetails authDetails;

    private UserDetails details;

    public JwtAuthenticationToken(TokenDetails tokenDetails,
                                  AuthDetails authDetails,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.tokenDetails = tokenDetails;
        this.authDetails = authDetails;
    }

    public JwtAuthenticationToken(TokenDetails tokenDetails,
                                  AuthDetails authDetails,
                                  InailUserDetails userDetails,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.tokenDetails = tokenDetails;
        this.authDetails = authDetails;
        this.details = userDetails;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return details;
    }

    public AuthDetails getAuthDetails() {
        return authDetails;
    }

    public TokenDetails getTokenDetails() {
        return tokenDetails;
    }
}
