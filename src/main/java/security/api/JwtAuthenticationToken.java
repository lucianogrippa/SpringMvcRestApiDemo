/**
 * 
 */
package security.api;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import dtos.JwtUser;
import entities.User;
import helpers.JwtHelper;
import helpers.LogHelper;
import services.UserService;

/**
 * @author luciano
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 1L;

	@Autowired
	LogHelper logger;

	@Autowired
	static
	JwtHelper jwtHelper;

	@Autowired
	static
	UserService userService;
	
	private JwtUser tokenUser;

	public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities,Object credentials) {
		super(authorities, credentials);
	}

	@Override
	public Object getCredentials() {
		UserCredentials credentials = new UserCredentials(tokenUser.getUsername(), tokenUser.getJwt());
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return tokenUser;
	}

	public String getToken() {
		return tokenUser != null ? tokenUser.getJwt():"";
	}

	public static JwtAuthenticationToken fromAuth(Authentication authentication) throws MalformedClaimException, UnsupportedEncodingException, JoseException {
		
		JwtAuthenticationToken jauth =  new JwtAuthenticationToken(authentication.getAuthorities(), authentication.getCredentials());
		jauth.setAuthenticated(false);
		jauth.setDetails(null);
		String authToken =  jauth.getCredentials().toString();
		JwtUser user = jwtHelper.parseToken(authToken);
		if(user != null && user.getId()>0) {
			// cerca nel repository
			User userAuth = userService.authUserWithToken(user.getUsername(),user.getId());
			if(userAuth != null) {
				jauth.setAuthenticated(true);
				jauth.setDetails(userAuth);
				return jauth;
			}
		}
	
		return null;
	}


	public void setTokenUser(JwtUser tokenUser) {
		this.tokenUser = tokenUser;
	}
}
