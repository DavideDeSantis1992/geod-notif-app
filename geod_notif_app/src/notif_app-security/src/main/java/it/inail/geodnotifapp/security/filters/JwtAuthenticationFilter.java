//package it.inail.geodnotifapp.security.filters;
//
//import it.inail.geodnotifapp.security.exceptions.UserAuthenticationException;
//import it.inail.geodnotifapp.security.models.AuthDetails;
//import it.inail.geodnotifapp.security.models.JwtAuthenticationToken;
//import it.inail.geodnotifapp.security.models.TokenDetails;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.MDC;
//import org.springframework.boot.info.BuildProperties;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Collections;
//
///**
// * Classe che estende la classe AbstractAuthenticationProcessingFilter per la gestione del contesto di sicurezza.
// * La classe ha il compito di verificare se la request è valida ed autenticata, cioè se contiene le informazioni obbligatorie
// * come il token JWT e il client_id. Se la request è autenticata viene valorizzato un contesto di sicurezza, altrimenti viene
// * generato un errore.
// */
//public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//
//    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//
//    private final BuildProperties buildProperties;
//
//    private final JwtTokenHelper jwtTokenHelper;
//
//    public JwtAuthenticationFilter(RequestMatcher requestMatcher,
//                                   BuildProperties buildProperties,
//                                   JwtTokenHelper jwtTokenHelper) {
//        super(requestMatcher);
//        this.buildProperties = buildProperties;
//        this.jwtTokenHelper = jwtTokenHelper;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//
//        //Extract info from request
//        AuthDetails authDetails = getRequestDetails(request);
//
//        //Validate and extract info from jwt
//        TokenDetails tokenDetails = validateToken(request, authDetails);
//
//        //set logging
//        setLoggedUserInfo(authDetails, tokenDetails);
//
//        JwtAuthenticationToken authentication = new JwtAuthenticationToken(
//                tokenDetails,
//                authDetails,
//                Collections.emptyList());
//
//        //set authentication for restTemplate
//        setSecurityContext(authentication);
//        return getAuthenticationManager().authenticate(authentication);
//    }
//
//    private void setSecurityContext(Authentication authResult) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authResult);
//        SecurityContextHolder.setContext(context);
//    }
//
//    /**
//     * Verifica che il token sia presente in request e controlla se è valido e non scaduto.
//     */
//    private TokenDetails validateToken(HttpServletRequest request, AuthDetails authDetails) {
//        String token = request.getHeader(SecurityConstants.HEADER_KEY_NAME);
//        if (token != null && token.startsWith(SecurityConstants.BEARER_PREFIX)) {
//            token = token.substring(SecurityConstants.BEARER_PREFIX.length());
//            return jwtTokenHelper.validateToken(token);
//        }
//        throw new UserAuthenticationException("Token JWT non trovato.");
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//        log.debug("Successful authentication for user "+ authResult.getName());
//        setSecurityContext(authResult);
//        chain.doFilter(request, response);
//    }
//
//
//    protected void unsuccessfulAuthentication(HttpServletRequest request,
//                                              HttpServletResponse response,
//                                              AuthenticationException failed) throws IOException, ServletException {
//
//        SecurityContextHolder.clearContext();
//        log.error("Unsuccessful Authenticaion reason: {}.", failed.getMessage());
//        super.unsuccessfulAuthentication(request, response, failed);
//    }
//
//    /**
//     * Viene valorizzato l'utente loggato per il log.
//     * @param authDetails
//     * @param tokenDetails
//     */
//    private void setLoggedUserInfo(AuthDetails authDetails, TokenDetails tokenDetails) {
//        MDC.put(SecurityConstants.API_SID, tokenDetails.getSid());
//        MDC.put(SecurityConstants.API_USER, tokenDetails.getSubject());
//        if (buildProperties != null) {
//            MDC.put(SecurityConstants.TAG_VERSIONE, buildProperties.getVersion());
//            MDC.put(SecurityConstants.TAG_DOMINIO, buildProperties.getArtifact());
//        }
//    }
//
//    /**
//     * Recupera i dati dell'utente dal header X-INAIL-DATA.
//     * Verifica che il client_id del chiamante sia valorizzato.
//     *
//     * @param request the request
//     * @return AuthDetails
//     */
//    private AuthDetails getRequestDetails(HttpServletRequest request) {
//        String clientId = request.getHeader(SecurityConstants.CLIENT_ID_KEY);
//        if (clientId == null) {
//            logger.error("CliendId must not be null");
//            throw new UserAuthenticationException("User is not authenticated");
//        }
//        String sid = null;
//        String csrfToken = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (int i = 0; i < cookies.length; i++) {
//                if ("SID".equals(cookies[i].getName())) {
//                    sid = cookies[i].getValue();
//                } else if ("CSRF-TOKEN".equals(cookies[i].getName())) {
//                    csrfToken = cookies[i].getValue();
//                }
//            }
//        }
//        SelectedUserInfo selectedUserInfo = extractCurrentUserInfo(request);
//
//        //Build new AuthDetails
//        return new AuthDetails(sid,
//                csrfToken,
//                selectedUserInfo.getHeadOffice(),
//                selectedUserInfo.getRole(),
//                selectedUserInfo.getOffice(),
//                clientId);
//    }
//
//    /**
//     * Estrae i dati dall'header X-INAIL-DATA se presente.
//     * @param request
//     * @return
//     */
//    private SelectedUserInfo extractCurrentUserInfo(HttpServletRequest request) {
//        String headerValue = request.getHeader(SecurityConstants.PROFILE_SELECTED_KEY);
//        String headOffice = null;
//        String role = null;
//        String office = null;
//
//        if (StringUtils.hasText(headerValue)) {
//            String[] values = StringUtils.delimitedListToStringArray(headerValue, SecurityConstants.PROFILE_DELIMITER);
//            headOffice = values != null && values.length > 0 ? values[0] : null;
//            role = values != null && values.length > 1 ? values[1] : null;
//            office = values != null && values.length > 2 ? values[2] : null;
//        }
//
//        return new SelectedUserInfo(headOffice, role, office);
//    }
//
//    private static class SelectedUserInfo {
//        private String headOffice;
//        private String role;
//        private String office;
//
//        public SelectedUserInfo(String headOffice, String role, String office) {
//            this.headOffice = headOffice;
//            this.role = role;
//            this.office = office;
//        }
//
//        public String getHeadOffice() {
//            return headOffice;
//        }
//
//        public String getRole() {
//            return role;
//        }
//
//        public String getOffice() {
//            return office;
//        }
//    }
//
//}
