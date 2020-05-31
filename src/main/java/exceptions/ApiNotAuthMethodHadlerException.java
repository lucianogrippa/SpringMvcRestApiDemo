package exceptions;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ApiNotAuthMethodHadlerException extends ServletException {

	private static final long serialVersionUID = -5631579370214649717L;

	private String httpMethod;
	private String requestURL;

	public ApiNotAuthMethodHadlerException() {
		super();
	}

	public ApiNotAuthMethodHadlerException(String httpMethod, String requestURL) {
		super("You should authorized to access");
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
