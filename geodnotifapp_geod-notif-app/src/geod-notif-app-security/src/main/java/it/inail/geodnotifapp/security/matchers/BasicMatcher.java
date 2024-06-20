package it.inail.geodnotifapp.security.matchers;

import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Map;

public interface BasicMatcher {

    Map<String, RequestMatcher> getMatchers() throws ClassNotFoundException;
}
