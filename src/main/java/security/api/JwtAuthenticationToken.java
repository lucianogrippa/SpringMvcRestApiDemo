/**
 * 
 */
package security.api;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import dtos.JwtUser;
import entities.User;
import exceptions.ApiExpairedTokenException;
import exceptions.ApiForbiddenHandlerException;
import helpers.JwtHelper;
import helpers.LogHelper;
import helpers.SpringContextProvider;
import services.UserService;

/**
 * @author luciano
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 1L;
	private boolean authenticated;
	@Autowired
	LogHelper logger;


	public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities,Object credentials) {
		super(authorities, credentials);
	}

	@Override
	public Object getCredentials() {
		return super.getCredentials();
	}

	@Override
	public Object getPrincipal() {
		return super.getPrincipal();
	}

	public String getToken() {
		return getCredentials().toString();
	}

	public static JwtAuthenticationToken fromAuth(Authentication authentication) throws MalformedClaimException, UnsupportedEncodingException, JoseException, ApiForbiddenHandlerException, ApiExpairedTokenException {
		UserService userService = SpringContextProvider.getApplicationContext().getBean(UserService.class);
		JwtHelper jwtHelper = SpringContextProvider.getApplicationContext().getBean(JwtHelper.class);
		
		JwtAuthenticationToken jauth =  new JwtAuthenticationToken(authentication.getAuthorities(), authentication.getCredentials());
		jauth.setAuthenticated(false);
		jauth.setDetails(null);
		String authToken =  jauth.getCredentials().toString();
		JwtUser user = jwtHelper.parseToken(authToken);
		
		if(user != null && user.getId()>0) {
			// cerca nel repository
			User userAuth = userService.authUserWithUUID(user.getUsername(),user.getId());
			if(userAuth != null) {
				jauth.setAuthenticated(true);
				jauth.setDetails(userAuth);
				return jauth;
			}
		}
	
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
}
