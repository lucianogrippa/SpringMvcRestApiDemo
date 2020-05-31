package exceptions;

import javax.servlet.ServletException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiBadRequestHandlerException extends ServletException {

	private String httpMethod;
	private String requestURL;

	public ApiBadRequestHandlerException() {
		super();
	}
	
	public ApiBadRequestHandlerException(String httpMethod, String requestURL) {
		super("Bad request for " + httpMethod + " " + requestURL);
		this.setHttpMethod(httpMethod);
		this.setRequestURL(requestURL);
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}
}
