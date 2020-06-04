package security.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import exceptions.JwtTokenMissingException;
import helpers.JwtHelper;
import helpers.LogHelper;
import security.ApiGrantedAuthority;

/**
 * 
 * @author luciano
 *
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	@Autowired LogHelper logger;
	@Autowired JwtHelper jwtHelper;
	
	
	public JwtAuthenticationFilter() {
		super("/**");
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		Authentication auth = null;
		// ottiene l'header Authorization
		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			throw new JwtTokenMissingException("No JWT token found in request headers");
		}
		try {
			// rimuovi il Bearer
			String authToken = header.substring(6).trim();
		
			//TODO: CARICARE LE AUTORITIES DAL DB
			List<ApiGrantedAuthority> authorities = new ArrayList<ApiGrantedAuthority>();
			authorities.add(new ApiGrantedAuthority("ROLE_USER"));
			authorities.add(new ApiGrantedAuthority("ROLE_ADMIN"));
			authorities.add(new ApiGrantedAuthority("ROLE_EDITOR"));
			
			JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authorities,authToken);
			// authentica la richiesta 
			auth = getAuthenticationManager().authenticate(authRequest);
		} catch (Exception e) {
			logger.logException(e);
			throw new JwtTokenMissingException("Error in Jwt Authentication header",e);
		}
		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);

		// As this authentication is in HTTP header, after success we need to continue
		// the request normally
		// and return the response as if the resource was not secured at all
		chain.doFilter(request, response);
	}
}
