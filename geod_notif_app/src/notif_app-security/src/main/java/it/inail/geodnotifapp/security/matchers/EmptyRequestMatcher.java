package it.inail.geodnotifapp.security.matchers;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe dummy per implementa un Matcher vuoto.
 */
public class EmptyRequestMatcher implements RequestMatcher {

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        return false;
    }
}
