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

	public JwtAuthenticationProvider() {
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			return JwtAuthenticationToken.fromAuth(authentication);
		} catch (MalformedClaimException | UnsupportedEncodingException | JoseException e) {
			logger.logException(e);
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
