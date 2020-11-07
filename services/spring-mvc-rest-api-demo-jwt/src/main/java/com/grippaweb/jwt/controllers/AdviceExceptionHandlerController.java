package com.grippaweb.jwt.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.grippaweb.jwt.dtos.ApiErrorMessageResponse;
import com.grippaweb.jwt.exceptions.ApiBadRequestHandlerException;
import com.grippaweb.jwt.exceptions.ApiForbiddenHandlerException;
import com.grippaweb.jwt.exceptions.ApiInternalServerErrorHandlerException;
import com.grippaweb.jwt.exceptions.ApiMethodNotAllowedHandlerException;
import com.grippaweb.jwt.exceptions.ApiNotAcceptedHandlerException;
import com.grippaweb.jwt.exceptions.ApiNotAuthMethodHadlerException;
import com.grippaweb.jwt.exceptions.ApiNotFoundHandlerException;
import com.grippaweb.jwt.helpers.LogHelper;

@ControllerAdvice
public class AdviceExceptionHandlerController extends ResponseEntityExceptionHandler  {
	
    	LogHelper logger = new LogHelper(getClass());
	
	@ExceptionHandler({
	    ApiNotFoundHandlerException.class
	})
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@Nullable
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> requestHandlingNoHandlerFound(HttpServletRequest req,
			ApiNotFoundHandlerException ex) {
		logger.logDebug("<<<<<<<<<<<<<<<<<<<<<<<< api not found method >>>>>>>>>>>>>>>>>");

		ApiErrorMessageResponse errorInfo = ex.getResponseDto();

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.NOT_FOUND);
		return responseApi;
	}
	
	@ExceptionHandler(value = { ApiBadRequestHandlerException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> badRequest(HttpServletRequest req, ApiBadRequestHandlerException ex) {
		logger.logDebug("in api bad request error handler");
		ApiErrorMessageResponse errorInfo = ex.getResponseDto();

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.BAD_REQUEST);
		return responseApi;
	}
	
	
	@ExceptionHandler(value = { ApiNotAuthMethodHadlerException.class })
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> badUnauthorized(HttpServletRequest req, ApiNotAuthMethodHadlerException ex) {
		logger.logDebug("in api unauthorizated request service");


		ApiErrorMessageResponse errorInfo = ex.getResponseDto();

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.UNAUTHORIZED);
		return responseApi;
	}
	
	@ExceptionHandler(value = { ApiForbiddenHandlerException.class })
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> badForbidden(HttpServletRequest req, ApiForbiddenHandlerException ex) {
		logger.logDebug("in api forbidden request service");
		
		ApiErrorMessageResponse errorInfo = ex.getResponseDto();

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.FORBIDDEN);
		return responseApi;
	}
	
	@ExceptionHandler(value = { ApiNotAcceptedHandlerException.class })
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> notAccettable(HttpServletRequest req, ApiNotAcceptedHandlerException ex) {
		logger.logDebug("in api not accettable request service");
		
		ApiErrorMessageResponse errorInfo = ex.getResponseDto();

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.NOT_ACCEPTABLE);
		return responseApi;
	}
	
	@ExceptionHandler(value = { ApiInternalServerErrorHandlerException.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> badInternalServerError(HttpServletRequest req, ApiInternalServerErrorHandlerException ex) {
		logger.logDebug("in api internal server error request service");

		ApiErrorMessageResponse errorInfo = ex.getResponseDto();
		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.INTERNAL_SERVER_ERROR);
		return responseApi;
	}
	
	@ExceptionHandler(value = { ApiMethodNotAllowedHandlerException.class })
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> badMethodNotAllowed(HttpServletRequest req, ApiMethodNotAllowedHandlerException ex) {
		logger.logDebug("in api internal server error request service");

		
		ApiErrorMessageResponse errorInfo = ex.getResponseDto();

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.METHOD_NOT_ALLOWED);
		return responseApi;
	}
}
