/**
 * 
 */
package security.api;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import exceptions.JwtTokenMissingException;
/**
 * @author luciano
 *
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 1L;
	
	
	public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	
	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}
	
	public static JwtAuthenticationToken fromToken(String token) throws JwtTokenMissingException {
		return null;
	}


	public String getToken() {
		// TODO Auto-generated method stub
		return null;
	}


	public static JwtAuthenticationToken fromAuth(UsernamePasswordAuthenticationToken authentication) {
		// TODO Auto-generated method stub
		return null;
	}
}
