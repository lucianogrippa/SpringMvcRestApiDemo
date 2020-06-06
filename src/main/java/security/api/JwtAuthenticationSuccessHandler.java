/**
 * 
 */
package security.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Dopo l'autenticazione se corretta viene chiamto il metodo
 * onAuthenticationSuccess Nel caso dellla gestione del token non e' necessario
 * alcun redirect
 * 
 * @author luciano
 */
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	public JwtAuthenticationSuccessHandler() {
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(authentication);
//		HttpSession session = request.getSession(true);
//		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
	}

}
