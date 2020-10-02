package com.grippaweb.usersmanager.exceptions;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.grippaweb.usersmanager.dtos.ApiErrorMessageResponse;
import com.grippaweb.usersmanager.helpers.ErrorHandlerHelper;
import com.grippaweb.usersmanager.helpers.LogHelper;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiInternalServerErrorHandlerException extends ServletException {

	private static final long serialVersionUID = 1L;
	LogHelper logger = new LogHelper(getClass());

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
		if (getMessage() != null && !getMessage().isEmpty()) {
			description = getMessage();
		} else
		if (getStackTrace() != null) {
			description = StringUtils.isEmpty(getRootCause()) ?  ErrorHandlerHelper.getStackTrace(getMessage(), getStackTrace()):
			    ErrorHandlerHelper.getStackTrace(getRootCause());
		}  else {
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
