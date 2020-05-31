/**
 * 
 */
package exceptions;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class ApiMethodNotAllowedHandlerException extends ServletException {
	private static final long serialVersionUID = 1L;
	private String httpMethod;
	private String requestURL;

	public ApiMethodNotAllowedHandlerException() {
		super();
	}
	
	public ApiMethodNotAllowedHandlerException(String httpMethod, String requestURL) {
		super("Method not allowed -> "+httpMethod+" url "+requestURL);
		this.setHttpMethod(httpMethod);
		this.setRequestURL(requestURL);
	}
	
	public ApiMethodNotAllowedHandlerException(String httpMethod, String requestURL,String message) {
		super("Method not allowed: "+message);
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
