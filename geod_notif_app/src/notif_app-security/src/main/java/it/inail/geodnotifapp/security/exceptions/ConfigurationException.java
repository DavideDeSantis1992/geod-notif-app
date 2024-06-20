package it.inail.geodnotifapp.security.exceptions;

public class ConfigurationException extends RuntimeException {

	private static final long serialVersionUID = -2655410917732367762L;

	public ConfigurationException() {
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
