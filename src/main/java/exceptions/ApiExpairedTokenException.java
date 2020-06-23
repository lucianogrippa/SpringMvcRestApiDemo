/**
 * 
 */
package exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author lgrippa
 *
 */
public class ApiExpairedTokenException extends AuthenticationException {
	private static final long serialVersionUID = 1L;
	private long expireTime;
	
	public ApiExpairedTokenException(String message, Throwable exception, long expiretime) {
		super(message,exception);
		this.expireTime = expiretime;
	}

	public long getExpireTime() {
		return expireTime;
	}
	
}
