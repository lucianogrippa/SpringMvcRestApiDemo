/**
 * 
 */
package exceptions;

import org.springframework.security.core.AuthenticationException;

import helpers.LogHelper;

public class ApiAuthenticationTokenException extends AuthenticationException {
	private static final long serialVersionUID = -8709929646209115481L;

	public ApiAuthenticationTokenException(String msg) {
		super(msg);
		LogHelper.getLogger().logDebug(msg);
	}

	public ApiAuthenticationTokenException(String msg,Throwable e) {
		super(msg,e);
		
		LogHelper.getLogger().logDebug(msg);
	}
}
