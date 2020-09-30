package com.grippaweb.usersmanager.exceptions;

import javax.servlet.ServletException;

import com.grippaweb.usersmanager.helpers.LogHelper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.grippaweb.usersmanager.dtos.ApiErrorMessageResponse;
import com.grippaweb.usersmanager.helpers.ErrorHandlerHelper;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ApiNotAcceptedHandlerException extends ServletException {
	private static final long serialVersionUID = 1L;

	LogHelper logger = new LogHelper(getClass());

	public ApiNotAcceptedHandlerException() {
		super("Error " + HttpStatus.NOT_ACCEPTABLE.value() + ": request not accettable");
	}

	public ApiNotAcceptedHandlerException(String message) {
		super(message);
		logger.logDebug("in ApiNotAcceptedHandlerException " + message);
	}

	public ApiNotAcceptedHandlerException(String message, Throwable exc) {
		super(message, exc);
		logger.logDebug("in ApiNotAcceptedHandlerException " + message);
	}

	public ApiErrorMessageResponse getResponseDto() {

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		String description = "";
		if (getRootCause() != null) {
			description = ErrorHandlerHelper.getStackTrace(getRootCause());
		} else if (getMessage() != null && !getMessage().isEmpty()) {
			description = getMessage();
		} else {
			description = " request not accettable";
		}

		errorInfo.setExeption(getRootCause());

		errorInfo.setData(null);
		errorInfo.setDescription(description);
		errorInfo.setError("API_REQUEST_NOT_ACCEPTABLE");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.NOT_ACCEPTABLE.value());

		return errorInfo;
	}
}
