package com.grippaweb.usersmanager.exceptions;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.grippaweb.usersmanager.dtos.ApiErrorMessageResponse;
import com.grippaweb.usersmanager.helpers.ErrorHandlerHelper;
import com.grippaweb.usersmanager.helpers.LogHelper;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ApiNotAuthMethodHadlerException extends ServletException {

	private static final long serialVersionUID = 1L;

	LogHelper logger = new LogHelper(getClass());

	public ApiNotAuthMethodHadlerException() {
		super("Error " + HttpStatus.UNAUTHORIZED.value() + ": permission denied for access to resource");
	}

	public ApiNotAuthMethodHadlerException(String message) {
		super(message);
		logger.logDebug("in ApiNotAuthMethodHadlerException " + message);
	}

	public ApiNotAuthMethodHadlerException(String message, Throwable exc) {
		super(message, exc);
		logger.logDebug("in ApiNotAuthMethodHadlerException " + message);
	}

	public ApiErrorMessageResponse getResponseDto() {

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		String description = "";
		if (getRootCause() != null) {
			description = ErrorHandlerHelper.getStackTrace(getRootCause());
		} else if (getMessage() != null && !getMessage().isEmpty()) {
			description = getMessage();
		} else {
			description = "Permission denied for access to resource";
		}

		errorInfo.setExeption(getRootCause() != null ? getRootCause() : getCause());

		errorInfo.setData(null);
		errorInfo.setDescription(description);
		errorInfo.setError("API_RESOURCE_UNAUTHORIZED");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.UNAUTHORIZED.value());

		return errorInfo;
	}
}
