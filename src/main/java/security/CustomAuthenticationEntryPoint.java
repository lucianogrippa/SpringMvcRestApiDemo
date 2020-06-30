package security;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import helpers.LogHelper;

public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	public static final String REALM_NAME = "grippaweb.eu";
	
	@Autowired
	LogHelper logger;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		response.addHeader("WWW-Authenticate", "Basic realm=\"" + REALM_NAME + "\"");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				authException.getMessage());
	}
	
    @Override
    public void afterPropertiesSet() {
        setRealmName(REALM_NAME);
        try {
			super.afterPropertiesSet();
		} catch (Exception e) {
			logger.logException(e);
		}
    }
}
