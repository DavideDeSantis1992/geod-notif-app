package it.inail.geodnotifapp.security.client;

import it.inail.geodnotifapp.security.filters.SecurityConstants;
import it.inail.geodnotifapp.security.models.AuthDetails;
import it.inail.geodnotifapp.security.models.JwtAuthenticationToken;
import it.inail.geodnotifapp.security.configuration.AuthorizationProperties;
import it.inail.geodnotifapp.security.models.TokenDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

/**
 * Classe che effettua la propagazione del contesto di sicurezza nelle chiamate HTTP tramite RestTemplate.
 * In particolare, vengono valorizzati:
 *  Header Authorization con il token JWT
 *  Header client_id con il clientId del servizio
 * Se il contesto di sicurezza non è valorizzato la chiamata HTTP viene effettuato comunque.
 */
public class AuthorizedClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthorizedClientHttpRequestInterceptor.class);

    private final AuthorizationProperties authorizationProperties;

    public AuthorizedClientHttpRequestInterceptor(AuthorizationProperties authorizationProperties) {
        this.authorizationProperties = authorizationProperties;
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {
        log.trace("AuthorizedClientHttpRequestInterceptor");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication instanceof JwtAuthenticationToken) {

            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)authentication;
            AuthDetails details = jwtAuthenticationToken.getAuthDetails();
            TokenDetails tokenDetails = jwtAuthenticationToken.getTokenDetails();

            if (details != null) {
                request.getHeaders().set(SecurityConstants.HEADER_KEY_NAME, SecurityConstants.BEARER_PREFIX + tokenDetails.getToken());
                request.getHeaders().set(SecurityConstants.CLIENT_ID_KEY, authorizationProperties.getClientId());

                request.getHeaders().set(SecurityConstants.PROFILE_SELECTED_KEY, details.buildCurrentProfileData());
                request.getHeaders().add("Cookie", "SID="+tokenDetails.getSid()
                        +";CSRF-TOKEN="+details.getCsrfToken());
            }
        }

        return execution.execute(request, body);
    }
}