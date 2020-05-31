/**
 * 
 */
package controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import dtos.ApiErrorMessageResponse;
import exceptions.ApiBadRequestHandlerException;
import exceptions.ApiForbiddenHandlerException;
import exceptions.ApiInternalServerErrorHandlerException;
import exceptions.ApiMethodNotAllowedHandlerException;
import exceptions.ApiNotFoundException;
import exceptions.ApiNotAuthMethodHadlerException;
import helpers.ErrorHandlerHelper;
import helpers.LogHelper;

@ControllerAdvice
public class AdviceExceptionHandlerController {

	@Autowired
	LogHelper logger;

	/**
	 * Api not found
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ApiNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> requestHandlingNoHandlerFound(HttpServletRequest req,
			ApiNotFoundException ex) {
		logger.logDebug("<<<<<<<<<<<<<<<<<<<<<<<< api not found method >>>>>>>>>>>>>>>>>");

		String errorURL = ex.getRequestURL();
		logger.logDebug("in api not found service >> " + errorURL);

		String errorMessage =  ex.getMessage();

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		errorInfo.setData(null);
		errorInfo.setDescription(errorMessage);
		errorInfo.setError("API_NOT_FOUND");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.NOT_FOUND.value());

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.NOT_FOUND);
		return responseApi;
	}
	
	@ExceptionHandler(value = { ApiBadRequestHandlerException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> badRequest(HttpServletRequest req, ApiBadRequestHandlerException ex) {
		logger.logDebug("in api bad request service");

		String errorURL = ex.getRequestURL();
		logger.logDebug("in api bad request service >> " + errorURL);

		String errorMessage = ex.getMessage();

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		errorInfo.setData(null);
		errorInfo.setDescription(errorMessage);
		errorInfo.setError("API_BAD_REQUEST");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.BAD_REQUEST.value());

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.BAD_REQUEST);
		return responseApi;
	}
	
	
	@ExceptionHandler(value = { ApiNotAuthMethodHadlerException.class })
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> badUnauthorized(HttpServletRequest req, ApiNotAuthMethodHadlerException ex) {
		logger.logDebug("in api unauthorizated request service");

		String errorURL = ex.getHttpMethod();
		logger.logDebug("in api unauthorizated request service >> " + errorURL);

		String errorMessage = ex.getMessage();

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		errorInfo.setData(null);
		errorInfo.setDescription(errorMessage);
		errorInfo.setError("API_UNAUTHORIZED");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.UNAUTHORIZED.value());

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.UNAUTHORIZED);
		return responseApi;
	}
	
	@ExceptionHandler(value = { ApiForbiddenHandlerException.class })
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> badForbidden(HttpServletRequest req, ApiForbiddenHandlerException ex) {
		logger.logDebug("in api forbidden request service");

		String errorURL = ex.getHttpMethod();
		logger.logDebug("in api forbidden request service >> " + errorURL);

		String errorMessage = ex.getMessage();

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		errorInfo.setData(null);
		errorInfo.setDescription(errorMessage);
		errorInfo.setError("API_FORBIDDEN");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.FORBIDDEN.value());

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.FORBIDDEN);
		return responseApi;
	}
	
	@ExceptionHandler(value = { ApiInternalServerErrorHandlerException.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> badInternalServerError(HttpServletRequest req, ApiInternalServerErrorHandlerException ex) {
		logger.logDebug("in api internal server error request service");

		String errorURL = ex.getHttpMethod();
		logger.logDebug("in api forbidden request service >> " + errorURL);

		String errorMessage = ex.getMessage();
		String descriptionMessage = ErrorHandlerHelper.getStatTrace(ex);

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		errorInfo.setData(errorMessage);
		errorInfo.setDescription(descriptionMessage);
		errorInfo.setError("API_INTERNAL_SERVER_ERROR");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.INTERNAL_SERVER_ERROR);
		return responseApi;
	}
	
	@ExceptionHandler(value = { ApiMethodNotAllowedHandlerException.class })
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> badMethodNotAllowed(HttpServletRequest req, ApiMethodNotAllowedHandlerException ex) {
		logger.logDebug("in api internal server error request service");

		String errorURL = ex.getHttpMethod();
		logger.logDebug("in api forbidden request service >> " + errorURL);

		String errorMessage = ex.getMessage();
		String descriptionMessage = ErrorHandlerHelper.getStatTrace(ex);

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		errorInfo.setData(errorMessage);
		errorInfo.setDescription(descriptionMessage);
		errorInfo.setError("API_IMETHOD_NOT_ALLOWED");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.METHOD_NOT_ALLOWED);
		return responseApi;
	}
	
	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	@ResponseBody
	protected ResponseEntity<ApiErrorMessageResponse> handleConflict(RuntimeException ex, WebRequest request) {
		logger.logDebug("in handleConflict advice");

		logger.logDebug("in handleConflict api service >> " + ex.getMessage());
		logger.logException(ex);
		String errorMessage = "Error: "+ ErrorHandlerHelper.getStatTrace(ex);

		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		errorInfo.setData(null);
		errorInfo.setDescription(errorMessage);
		errorInfo.setError("API_EXCEPTION");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.INTERNAL_SERVER_ERROR);
		return responseApi;
	}
}
