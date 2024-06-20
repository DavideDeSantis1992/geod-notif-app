package it.inail.geodnotifapp.exceptions;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4395918559886271287L;

	public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
