package exceptions;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ApiForbiddenHandlerException extends ServletException {
	private static final long serialVersionUID = 1L;
	
	private String httpMethod;
	private String requestURL;

	public ApiForbiddenHandlerException() {
		super();
	}
	
	public ApiForbiddenHandlerException(String httpMethod, String requestURL) {
		super("No handler found for " + httpMethod + " " + requestURL);
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
