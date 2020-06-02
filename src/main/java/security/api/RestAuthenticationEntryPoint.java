package security.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Entrypoint invocato quando un api viene chiamata 
 * senza token , quindi verra' lanciato l'errore SC_UNAUTHORIZED
 * 
 * @author luciano
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	public RestAuthenticationEntryPoint() {}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		// invia l'errore non autorizzato
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		
	}

}
