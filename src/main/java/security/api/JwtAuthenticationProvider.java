/**
 * 
 */
package security.api;

import java.util.List;

import org.jose4j.jwt.MalformedClaimException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import dtos.JwtUser;
import exceptions.JwtTokenMalformedException;
import helpers.JwtHelper;
import helpers.LogHelper;

/**
 * @author luciano
 *
 */
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	JwtHelper jwtUtil;

	@Autowired
	LogHelper logger;

	public JwtAuthenticationProvider() {

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// TODO Auto-generated method stub

	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		JwtAuthenticationToken jwtAuthenticationToken = JwtAuthenticationToken.fromAuth(authentication);
		String token = jwtAuthenticationToken.getToken();

		JwtUser parsedUser;
		try {
			parsedUser = jwtUtil.parseToken(token);

			if (parsedUser == null) {
				throw new JwtTokenMalformedException("JWT token is not valid");
			}

			List<GrantedAuthority> authorityList = AuthorityUtils
					.commaSeparatedStringToAuthorityList(parsedUser.getRole());

			return new User(parsedUser.getUsername(), token, authorityList);
		} catch (MalformedClaimException | JwtTokenMalformedException e) {
			logger.logException(e);
		}
		return null;
	}

}
