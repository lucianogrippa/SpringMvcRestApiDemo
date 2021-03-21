/**
 * 
 */
package security.api;

import java.io.UnsupportedEncodingException;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import exceptions.ApiAuthenticationTokenException;
import exceptions.ApiExpairedTokenException;
import exceptions.ApiForbiddenHandlerException;
import helpers.JwtHelper;
import helpers.LogHelper;

/**
 * @author luciano
 *
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	JwtHelper jwtUtil;

	@Autowired
	LogHelper logger;
	
	private boolean expired;
	
	private AuthenticationException exceptionCause;

	public JwtAuthenticationProvider() {
	}

	@Override
	public Authentication authenticate(Authentication authentication) {
		Authentication authUser =null;
		exceptionCause= null;
		try {
			authUser = JwtAuthenticationToken.fromAuth(authentication);
		} catch (MalformedClaimException | UnsupportedEncodingException
				| JoseException   e) {
			logger.logException(e);
			exceptionCause = new ApiAuthenticationTokenException("Can't authenticate with provided token" ,e);
			expired=false;
		}catch(ApiForbiddenHandlerException e) {
			logger.logException(e);
			exceptionCause = new ApiAuthenticationTokenException("Can't authenticate with provided token" ,e);
		}
		catch (ApiExpairedTokenException e) {
			logger.logException(e);
			expired = true;
			exceptionCause = e;
		}
		
		if(authUser == null && exceptionCause == null) {
			exceptionCause = new ApiAuthenticationTokenException("Token error or wrong credentials");
		}
		return authUser;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	public boolean isExpired() {
		return expired;
	}

	protected void setExpired(boolean expired) {
		this.expired = expired;
	}

	public AuthenticationException getExceptionCause() {
		return exceptionCause;
	}

}
