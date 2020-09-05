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
import org.springframework.util.StringUtils;

import entities.User;
import exceptions.ApiAuthenticationTokenException;
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
	@Autowired
	LogHelper logger;
	@Autowired
	JwtHelper jwtHelper;

	@Autowired
	JwtAuthenticationProvider authenticationProvider;
	
	@Autowired
	JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
	
	@Autowired
	JwtAuthenticationFailureHandler jwAuthenticationFailureHandler;

	public JwtAuthenticationFilter() {
		super("/**");
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return super.requiresAuthentication(request, response);
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

			// TODO: CARICARE LE AUTORITIES DAL DB
			List<ApiGrantedAuthority> authorities = new ArrayList<ApiGrantedAuthority>();
			authorities.add(new ApiGrantedAuthority("ROLE_USER"));
			authorities.add(new ApiGrantedAuthority("ROLE_ADMIN"));
			authorities.add(new ApiGrantedAuthority("ROLE_EDITOR"));

			JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authorities, authToken);
			// authentica la richiesta
			auth = authenticationProvider.authenticate(authRequest);
			
			if (auth != null && auth.getPrincipal() != null  && auth.isAuthenticated()) {
				User userPrincipal = (User) auth.getDetails();
				ApiGrantedAuthority userAuthority   = authorities.stream()
						  .filter(a->userPrincipal.getRoles().iterator().next().getCode().equals(a.getAuthority()))
						  .findAny()
						  .orElse(null);
				
				if(userAuthority != null && !StringUtils.isEmpty(userAuthority.getAuthority())) {
						getSuccessHandler().onAuthenticationSuccess(request, response, authRequest);
				}
				else
				{
					if(auth != null)
						auth= null;
					getFailureHandler().onAuthenticationFailure(request, response,new ApiAuthenticationTokenException("User role not have grant access to the api"));
				}
			} else {
				if(auth != null)
					auth= null;
				getFailureHandler().onAuthenticationFailure(request, response,
						authenticationProvider.getExceptionCause());
			}
		} catch (Exception e) {
			logger.logException(e);
			throw new JwtTokenMissingException("Error in Jwt Authentication header", e);
		}
		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);

		// questa chiamata seve a far continare normalmente la chiamata
		//jwtAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
		chain.doFilter(request, response);
	}
}
