package com.grippaweb.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grippaweb.dtos.ApiErrorMessageResponse;
import com.grippaweb.exceptions.ApiBadRequestHandlerException;
import com.grippaweb.exceptions.ApiForbiddenHandlerException;
import com.grippaweb.exceptions.ApiInternalServerErrorHandlerException;
import com.grippaweb.exceptions.ApiMethodNotAllowedHandlerException;
import com.grippaweb.exceptions.ApiNotAcceptedHandlerException;
import com.grippaweb.exceptions.ApiNotAuthMethodHadlerException;
import com.grippaweb.exceptions.ApiNotFoundHandlerException;
import com.grippaweb.helpers.LogHelper;

@RestController
public class HttpApiDefaultErrorPageController implements ErrorController {
    @Autowired
    LogHelper logger;

    @RequestMapping(value="/error",produces = "application/json")
    @ResponseBody
    public ApiErrorMessageResponse handleError(MethodArgumentNotValidException exception, HttpServletRequest request,
	    HttpServletResponse resp) throws ApiNotFoundHandlerException, ApiBadRequestHandlerException {

	Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
	ApiErrorMessageResponse apiResp = new ApiErrorMessageResponse();

	switch (statusCode) {
	case 404:
	    apiResp = requestHandlingNoHandlerFound(request);
	    break;
	
	case 400:
	    requestHandlingBadRequest(request);
	    break;
	
	    
	
	default:
	    break;
	}

	return apiResp;
    }

    @Override
    public String getErrorPath() {
	return "/error";
    }

    private ApiErrorMessageResponse requestHandlingNoHandlerFound(HttpServletRequest req)
	    throws ApiNotFoundHandlerException {
	
	String origMessage = (String) req.getAttribute("javax.servlet.error.message");
	ApiNotFoundHandlerException notFoundEx = new ApiNotFoundHandlerException(origMessage);
	
	return notFoundEx.getResponseDto();
    }
    
    private void requestHandlingBadRequest(HttpServletRequest req) throws ApiBadRequestHandlerException {
	String origMessage = (String) req.getAttribute("javax.servlet.error.message");
	logger.logDebug("in /handle_400");
	throw new ApiBadRequestHandlerException(origMessage);
    }

    @GetMapping(value = "/api/handle_401")
    @ResponseBody
    public void requestHandlingNotAuth(HttpServletRequest req) throws ApiNotAuthMethodHadlerException {
	String origMessage = (String) req.getAttribute("javax.servlet.error.message");
	throw new ApiNotAuthMethodHadlerException(origMessage);
    }

    @GetMapping(value = "/handle_403")
    @ResponseBody
    public void requestHandlingForbidden(HttpServletRequest req) throws ApiForbiddenHandlerException {
	String origMessage = (String) req.getAttribute("javax.servlet.error.message");
	throw new ApiForbiddenHandlerException(origMessage);
    }

    @RequestMapping(value = "/api/handle_406")
    @ResponseBody
    public void requestHandlingNotAccettable(HttpServletRequest req) throws ApiNotAcceptedHandlerException {
	String origMessage = (String) req.getAttribute("javax.servlet.error.message");
	throw new ApiNotAcceptedHandlerException(origMessage);
    }

    @GetMapping(value = "/api/handle_405")
    @ResponseBody
    public void requestMethodNotAllowed(HttpServletRequest req) throws ApiMethodNotAllowedHandlerException {
	String origMessage = (String) req.getAttribute("javax.servlet.error.message");
	throw new ApiMethodNotAllowedHandlerException(origMessage);
    }

    @GetMapping(value = "/api/handle_500")
    @ResponseBody
    public void requestHandlingInternalServerError(HttpServletRequest req)
	    throws ApiInternalServerErrorHandlerException {
	String origMessage = (String) req.getAttribute("javax.servlet.error.message");
	throw new ApiInternalServerErrorHandlerException(origMessage);
    }
}
