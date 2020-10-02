package com.grippaweb.exceptions;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.grippaweb.dtos.ApiErrorMessageResponse;
import com.grippaweb.helpers.ErrorHandlerHelper;
import com.grippaweb.helpers.LogHelper;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ApiForbiddenHandlerException extends ServletException {
	private static final long serialVersionUID = 1L;

	LogHelper logger = LogHelper.getLogger();

	public ApiForbiddenHandlerException() {
		super("Error " + HttpStatus.FORBIDDEN.value() + ": forbidden access to resource");
	}

	public ApiForbiddenHandlerException(String message) {
		super(message);
		logger.logDebug("in ApiForbiddenHandlerException " + message);
	}

	public ApiForbiddenHandlerException(String message, Throwable exc) {
		super(message, exc);
		logger.logDebug("in ApiForbiddenHandlerException " + message);
	}

	public ApiErrorMessageResponse getResponseDto() {

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		String description = "";
		if (getRootCause() != null) {
			description = ErrorHandlerHelper.getStackTrace(getRootCause());
		} else if (getMessage() != null && !getMessage().isEmpty()) {
			description = getMessage();
		} else {
			description = "Forbidden access to resource";
		}

		errorInfo.setExeption(getRootCause() != null ? getRootCause() : getCause());

		errorInfo.setData(null);
		errorInfo.setDescription(description);
		errorInfo.setError("API_RESOURCE_FORBIDDEN");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.FORBIDDEN.value());

		return errorInfo;
	}
}
