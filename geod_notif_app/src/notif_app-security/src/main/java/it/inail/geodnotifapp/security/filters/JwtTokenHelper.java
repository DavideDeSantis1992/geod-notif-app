//package it.inail.geodnotifapp.security.filters;
//
//import it.inail.geodnotifapp.security.dto.WellknownEndpoint;
//import it.inail.geodnotifapp.security.services.WellknownService;
//import it.inail.geodnotifapp.security.configuration.AuthorizationProperties;
//import it.inail.geodnotifapp.security.exceptions.UserAuthenticationException;
//import it.inail.geodnotifapp.security.models.TokenDetails;
//import com.nimbusds.jose.JOSEException;
//import com.nimbusds.jose.JWSAlgorithm;
//import com.nimbusds.jose.jwk.source.JWKSource;
//import com.nimbusds.jose.jwk.source.RemoteJWKSet;
//import com.nimbusds.jose.proc.BadJOSEException;
//import com.nimbusds.jose.proc.JWSKeySelector;
//import com.nimbusds.jose.proc.JWSVerificationKeySelector;
//import com.nimbusds.jose.proc.SecurityContext;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
//import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
//import com.nimbusds.jwt.proc.DefaultJWTProcessor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.text.ParseException;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//
///**
// * Classe di utilità che si occupa della verifica del token JWT utilizzando la chiave JWKS del gateway.
// */
//@Component
//@DependsOn({"SSLCertificateProvider"})
//public class JwtTokenHelper {
//
//    private static final Logger log = LoggerFactory.getLogger(JwtTokenHelper.class);
//
//    private static final List<String> VALID_CLAIMS = Arrays.asList(new String[] {"sub", "iat", "iss", "exp", "jti", "authorities"});
//
//    private final AuthorizationProperties authorizationProperties;
//
//    private final WellknownService wellknownService;
//
//    private ConfigurableJWTProcessor<SecurityContext> jwtProcessor;
//
//    public JwtTokenHelper(AuthorizationProperties authorizationProperties, WellknownService wellknownService) {
//        this.authorizationProperties = authorizationProperties;
//        this.wellknownService = wellknownService;
//    }
//
//    @PostConstruct
//    public void initJwtProcessor() {
//
//        try {
//
//            jwtProcessor = new DefaultJWTProcessor<>();
//
//            JWKSource<SecurityContext> keySource = getKeySource();
//
//            JWSAlgorithm expectedJWSAlg = JWSAlgorithm.ES256;
//            JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(expectedJWSAlg, keySource);
//            jwtProcessor.setJWSKeySelector(keySelector);
//
//            jwtProcessor.setJWTClaimsSetVerifier(
//                    new DefaultJWTClaimsVerifier<>(
//                            new JWTClaimsSet.Builder()
//                                    .issuer(getValidIssuer())
//                                    .build(),
//                            new HashSet<>(VALID_CLAIMS)));
//
//        }catch (IOException e) {
//            log.error(e.getMessage(), e);
//            throw new UserAuthenticationException("Errore di inizializzazione del Parser JWT. "+e.getMessage());
//        }
//    }
//
//    private JWKSource<SecurityContext> getKeySource() throws MalformedURLException {
//        return new RemoteJWKSet<>(new URL(getRemoteJwtSetUrl()));
//    }
//
//    public TokenDetails validateToken(String token) {
//
//        try {
//
//            JWTClaimsSet claimsSet = jwtProcessor.process(token, null);
//
//            if (!checkAudience(claimsSet)) {
//                throw new UserAuthenticationException(String.format("Audience non valida %s.", claimsSet.getAudience()));
//            }
//
//            return TokenDetails.builder()
//                    .subject(claimsSet.getSubject())
//                    .sid(claimsSet.getJWTID())
//                    .expirationTime(claimsSet.getExpirationTime())
//                    .userGroups(getRoles(claimsSet))
//                    .authorities(getAuthorities(claimsSet))
//                    .selectedRole(getSelectedRole(claimsSet))
//                    .token(token)
//                    .build();
//
//        }catch (ParseException | JOSEException | BadJOSEException e) {
//            log.error(e.getMessage(), e);
//            throw new UserAuthenticationException("Token JWT non valido o scaduto. "+e.getMessage());
//        }
//    }
//
//    private String getRemoteJwtSetUrl() {
//        WellknownEndpoint wellKnownUrls = wellknownService.getWellKnownUrls();
//        return wellKnownUrls.getJwksUrl();
//    }
//
//    private boolean checkAudience(JWTClaimsSet claims) {
//        List<String> audiences = claims.getAudience();
//        return audiences.stream().allMatch(item -> authorizationProperties.getValidAudience().contains(item));
//    }
//
//
//    private List<String> getAuthorities(JWTClaimsSet claims) throws ParseException {
//        List<String> authorities = claims.getStringListClaim(JWTConstants.JWT_AUTHORITIES);
//        if (authorities == null) authorities =  Collections.emptyList();
//        return authorities;
//    }
//
//    private  List<String>  getRoles(JWTClaimsSet claims) throws ParseException{
//        List<String> roles = claims.getStringListClaim(JWTConstants.JWT_ROLES);
//        if (roles == null) roles =  Collections.emptyList();
//        return roles;
//    }
//
//    private String getSelectedRole(JWTClaimsSet claims) throws ParseException{
//        return claims.getStringClaim(JWTConstants.JWT_SELECTED_ROLE);
//    }
//
//    private String getValidIssuer() {
//        return wellknownService.getWellKnownUrls().getIssuer();
//    }
//
//}
