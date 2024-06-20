package it.inail.geodnotifapp.security.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import it.inail.geodnotifapp.security.configuration.AuthorizationProperties;
import it.inail.geodnotifapp.security.configuration.SSLProperties;
import it.inail.geodnotifapp.security.dto.JWTRequest;
import it.inail.geodnotifapp.security.dto.JWTResponse;
import it.inail.geodnotifapp.security.exceptions.TokenAuthenticationException;
import it.inail.geodnotifapp.security.exceptions.UserAuthenticationException;
import it.inail.geodnotifapp.security.services.JWTService;
import it.inail.geodnotifapp.security.services.WellknownService;
import it.inail.geodnotifapp.security.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Classe di tipo Service che implementa la chiamata verso il servizio CreateJWT esposto dal Gateway.
 * Tale servizio consente di autenticarsi con un utente di sistema per effettuare chiamate B2B.
 *
 */
@Service
public class JWTServiceImpl implements JWTService {

    private static final Logger log = LoggerFactory.getLogger(JWTServiceImpl.class);

    @Autowired
    private AuthorizationProperties authorizationProperties;

    @Autowired
    private SSLProperties sslProperties;

    @Autowired
    private WellknownService wellknownService;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public JWTResponse createJWT(String username) {

        try {

            URL url = new URL(authorizationProperties.getCreateJWTServiceUrl());

            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            HttpUtils.setDummyTLS(urlConnection, sslProperties.getHostnameVerify(), sslProperties.getTrustAllCertificates());

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("client_id", authorizationProperties.getClientId());
            urlConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
            urlConnection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            urlConnection.setDoOutput(true);

            JWTRequest request = new JWTRequest();
            request.setUsername(username);

            OutputStream outputStream = urlConnection.getOutputStream();
            objectMapper.writeValue(outputStream, request);

            urlConnection.connect();

            JWTResponse jwtResponse = null;
            try (InputStream inputStream = urlConnection.getInputStream()) {
                jwtResponse = objectMapper.readValue(inputStream, JWTResponse.class);
            }

            return jwtResponse;

        }catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
            log.error(e.getMessage(), e);
            throw new TokenAuthenticationException(e.getMessage(), e);
        }
    }


    @Override
    public String getAccessToken() {

        try {
            AuthorizationGrant clientGrant = new ClientCredentialsGrant();

            // The credentials to authenticate the client at the token endpoint
            ClientID clientID = new ClientID(authorizationProperties.getClientId());
            Secret clientSecret = new Secret(authorizationProperties.getAccessSecret());
            ClientAuthentication clientAuth = new ClientSecretBasic(clientID, clientSecret);

            //Get token endpoint
            URI tokenEndpoint = new URI(wellknownService.getB2BConfiguration().getTokenEndpoint());

            // Make the token request
            TokenRequest request = new TokenRequest(tokenEndpoint, clientAuth, clientGrant, null);

            HTTPRequest httpRequest = request.toHTTPRequest();

            if (!sslProperties.getHostnameVerify()) {
                log.debug("Set dummy Hostname verifier");
                //httpRequest.setHostnameVerifier(HttpUtils.DUMMY_HOSTNAME_VERIFIER);
            }

            if (sslProperties.getTrustAllCertificates()) {
                log.debug("Set dummy Socket Factory");
                //httpRequest.setSSLSocketFactory(HttpUtils.DUMMY_SOCKET_FACTORY);
            }

            TokenResponse response = TokenResponse.parse(httpRequest.send());

            if (!response.indicatesSuccess()) {
                TokenErrorResponse errorResponse = response.toErrorResponse();
                log.error(errorResponse.getErrorObject().getDescription());
                throw new UserAuthenticationException(errorResponse.getErrorObject().getDescription());
            }

            AccessTokenResponse successResponse = response.toSuccessResponse();
            AccessToken accessToken = successResponse.getTokens().getAccessToken();
            return (String) accessToken.toJSONObject().get("access_token");

        }catch (URISyntaxException | IOException | ParseException e) {
            log.error("Access Token Exception "+e.getMessage());
            throw new UserAuthenticationException(e.getMessage(),e);
        }
    }

}
