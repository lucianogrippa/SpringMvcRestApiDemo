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
import exceptions.ApiMethodNotFoundException;
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
	@ExceptionHandler(ApiMethodNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ApiErrorMessageResponse> requestHandlingNoHandlerFound(HttpServletRequest req,
			ApiMethodNotFoundException ex) {
		logger.logDebug("<<<<<<<<<<<<<<<<<<<<<<<< api not found method >>>>>>>>>>>>>>>>>");

		String errorURL = ex.getRequestURL();
		logger.logDebug("in api not found service >> " + errorURL);

		String errorMessage = "Request url " + errorURL + " is not valid";

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

		String errorMessage = "Paramiter missing or wrong type for method " + errorURL;

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
		errorInfo.setError("API_BAD_REQUEST");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.BAD_REQUEST.value());

		ResponseEntity<ApiErrorMessageResponse> responseApi = new ResponseEntity<ApiErrorMessageResponse>(errorInfo,
				HttpStatus.UNAUTHORIZED);
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
