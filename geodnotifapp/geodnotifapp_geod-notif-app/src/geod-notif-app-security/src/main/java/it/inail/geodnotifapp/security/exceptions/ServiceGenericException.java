package it.inail.geodnotifapp.security.exceptions;

public class ServiceGenericException extends RuntimeException {

	private static final long serialVersionUID = -5630728211247945672L;

	public ServiceGenericException() {
    }

    public ServiceGenericException(String message) {
        super(message);
    }

    public ServiceGenericException(String message, Throwable cause) {
        super(message, cause);
    }
}
