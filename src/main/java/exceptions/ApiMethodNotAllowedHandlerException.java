/**
 * 
 */
package exceptions;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import dtos.ApiErrorMessageResponse;
import helpers.ErrorHandlerHelper;
import helpers.LogHelper;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class ApiMethodNotAllowedHandlerException extends ServletException {

	private static final long serialVersionUID = 1L;
	LogHelper logger = LogHelper.getLogger();

	public ApiMethodNotAllowedHandlerException() {
		super("Error " + HttpStatus.METHOD_NOT_ALLOWED.value() + ": Exception: wrong parameters for method call");
	}

	public ApiMethodNotAllowedHandlerException(String message) {
		super(message);
		logger.logDebug("in ApiMethodNotAllowedHandlerException " + message);
	}

	public ApiMethodNotAllowedHandlerException(String message, Throwable exc) {
		super(message, exc);
		logger.logDebug("in ApiMethodNotAllowedHandlerException " + message);
	}

	public ApiErrorMessageResponse getResponseDto() {

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		String description = "";
		if (getRootCause() != null) {
			description = ErrorHandlerHelper.getStackTrace(getRootCause());
		} else if (getMessage() != null && !getMessage().isEmpty()) {
			description = getMessage();
		} else {
			description = "Wrong parameters for method call";
		}

		errorInfo.setExeption(getRootCause());

		errorInfo.setData(null);
		errorInfo.setDescription(description);
		errorInfo.setError("API_NOT_ALLOWED");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());

		return errorInfo;
	}
}
