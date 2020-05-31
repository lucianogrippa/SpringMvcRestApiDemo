package exceptions;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiInternalServerErrorHandlerException extends ServletException {

	private static final long serialVersionUID = 1L;
	private String httpMethod;
	private String requestURL;
	
	public ApiInternalServerErrorHandlerException(String message, Throwable rootCause) {
		super(message, rootCause);
	}
	
	public ApiInternalServerErrorHandlerException() {
		super();
	}

	public ApiInternalServerErrorHandlerException(String httpMethod, String requestURL) {
		super("Server Error 500");
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
