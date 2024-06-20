package it.inail.geodnotifapp.security.filters;

import it.inail.geodnotifapp.security.services.ProfileService;
import it.inail.geodnotifapp.security.configuration.AuthorizationProperties;
import it.inail.geodnotifapp.security.models.AuthDetails;
import it.inail.geodnotifapp.security.models.InailUserDetails;
import it.inail.geodnotifapp.security.models.JwtAuthenticationToken;
import it.inail.geodnotifapp.security.models.TokenDetails;
import it.inail.geodnotifapp.security.providers.TechRoleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    @Autowired
    private AuthorizationProperties authorizationProperties;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private TechRoleProvider techRoleProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken)authentication;

        InailUserDetails authenticatedUser = getAuthenticatedUser(jwtToken.getAuthDetails(), jwtToken.getTokenDetails());

        return new JwtAuthenticationToken(jwtToken.getTokenDetails(),
                jwtToken.getAuthDetails(),
                authenticatedUser,
                authenticatedUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }

    private InailUserDetails getAuthenticatedUser(AuthDetails authDetails, TokenDetails tokenDetails) {

        if (authorizationProperties.getCheckUserRole()) {
            if (!checkCurrentUserRoleAllowed(authDetails, tokenDetails)) {
                log.error(String.format("User role [%s] not allowed", authDetails.getRole()));
                throw new PreAuthenticatedCredentialsNotFoundException(String.format("User role [%s] not allowed", authDetails.getRole()));
            }
        }

        if (authorizationProperties.getCheckHeadOffice()) {
            if (!checkHeadOfficeAllowed(authDetails, tokenDetails)) {
                log.error(String.format("User head office [%s] is not allowed", authDetails.getHeadOffice()));
                throw new PreAuthenticatedCredentialsNotFoundException(String.format("User head [%s] office is not allowed", authDetails.getHeadOffice()));
            }
        }

        return new InailUserDetails(tokenDetails.getSubject(), getRoles(authDetails, tokenDetails));
    }

    /**
     * Method check if current role if allowed for user
     */
    private boolean checkCurrentUserRoleAllowed(AuthDetails authDetails, TokenDetails tokenDetails) {
        //Sono null se non è presente Header X-INAIL-DATA
        if (authDetails.getRole() == null) return true;
        return tokenDetails.getUserGroups().contains(authDetails.getRole());
    }

    private boolean checkHeadOfficeAllowed(AuthDetails authDetails, TokenDetails tokenDetails) {
        //Sono null se non è presente Header X-INAIL-DATA
        if(authDetails.getRole() == null && authDetails.getHeadOffice() == null) return true;
        return profileService.checkUserProfile(tokenDetails.getSid(),
               authDetails.getRole(),
               authDetails.getHeadOffice(),
               tokenDetails.getExpirationTime());
    }

    /**
     * Check if user groups
     * @param authDetails
     * @param tokenDetails
     * @return
     */
    private List<GrantedAuthority> getRoles(AuthDetails authDetails, TokenDetails tokenDetails) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        String currentRole = authDetails.getRole();
        if (authorizationProperties.getCheckUserRole()) {
            authorities.addAll(techRoleProvider.getTechRole(currentRole));
        }else {
            tokenDetails.getUserGroups().forEach(groupId -> authorities.addAll(techRoleProvider.getTechRole(groupId)));
        }
        return new ArrayList<>(authorities);
    }

}
