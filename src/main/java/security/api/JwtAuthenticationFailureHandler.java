package security.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import exceptions.ApiExpairedTokenException;
import helpers.LogHelper;

@Component(value = "jwtAuthenticationFailureHandler")
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	LogHelper logger;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if(exception instanceof ApiExpairedTokenException) {
			response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), exception.getMessage());
		}
		else
		{
			response.sendError(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
		}
		
	}
}
