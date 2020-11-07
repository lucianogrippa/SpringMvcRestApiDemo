package com.grippaweb.exceptions;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.grippaweb.dtos.ApiErrorMessageResponse;
import com.grippaweb.helpers.ErrorHandlerHelper;
import com.grippaweb.helpers.LogHelper;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiInternalServerErrorHandlerException extends ServletException {

	private static final long serialVersionUID = 1L;
	LogHelper logger = LogHelper.getLogger();

	public ApiInternalServerErrorHandlerException() {
		super("Error " + HttpStatus.INTERNAL_SERVER_ERROR.value() + ": Exception occurred on server");
	}

	public ApiInternalServerErrorHandlerException(String message) {
		super(message);
		logger.logDebug("in ApiInternalServerErrorHandlerException " + message);
	}

	public ApiInternalServerErrorHandlerException(String message, Throwable exc) {
		super(message, exc);
		logger.logDebug("in ApiInternalServerErrorHandlerException " + message);
	}

	public ApiErrorMessageResponse getResponseDto() {

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		String description = "";
		if (getStackTrace() != null) {
			description = ErrorHandlerHelper.getStackTrace(getRootCause());
		} else if (getMessage() != null && !getMessage().isEmpty()) {
			description = getMessage();
		} else {
			description = "Internal Server error occurred see exception";
		}

		errorInfo.setExeption(getRootCause() != null ? getRootCause() : getCause());

		errorInfo.setData(null);
		errorInfo.setDescription(description);
		errorInfo.setError("API_INTERNAL_SERVER_ERROR");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return errorInfo;
	}
}
