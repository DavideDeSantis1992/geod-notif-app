package it.inail.geodnotifapp.security.exceptions;

public class TokenAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -3203563871022810728L;

	public TokenAuthenticationException() {
    }

    public TokenAuthenticationException(String message) {
        super(message);
    }

    public TokenAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
