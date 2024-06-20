package it.inail.geodnotifapp.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = -2773126530317278447L;

    public UserAuthenticationException(String message) {
        super(message);
    }

    public UserAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
