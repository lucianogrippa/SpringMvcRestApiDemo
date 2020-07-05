package exceptions;

public class JwtTokenMalformedException extends Exception {
	private static final long serialVersionUID = 1L;

	public JwtTokenMalformedException(String message) {
		super(message);
	}

}
