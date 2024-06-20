package it.inail.geodnotifapp.security.utils;

import it.inail.geodnotifapp.security.models.JwtAuthenticationToken;
import it.inail.geodnotifapp.security.models.TokenDetails;
import org.springframework.security.core.Authentication;
import java.util.List;

public final class AuthenticationUtils {


    public static String getUsername(Authentication authentication) {
        return getTokenDetails(authentication).getSubject();
    }

    public static String getSelectedRole(Authentication authentication) {
        return getTokenDetails(authentication).getSelectedRole();
    }

    public static List<String> getUserGroups(Authentication authentication) {
        return getTokenDetails(authentication).getUserGroups();
    }

    public static List<String> getAuthorities(Authentication authentication) {
        return getTokenDetails(authentication).getAuthorities();
    }

    private static TokenDetails getTokenDetails(Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        return jwtAuthenticationToken.getTokenDetails();
    }

}
