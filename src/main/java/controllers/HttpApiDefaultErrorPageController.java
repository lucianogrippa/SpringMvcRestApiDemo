package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import dtos.ApiErrorMessageResponse;
import exceptions.ApiBadRequestHandlerException;
import exceptions.ApiMethodNotFoundException;
import exceptions.ApiNotAuthMethodHadlerException;
import helpers.LogHelper;

@RestController
@RequestMapping("/api")
public class HttpApiDefaultErrorPageController {
	@Autowired
	LogHelper logger;

	@RequestMapping(value = "/handle_404")
	@ResponseBody
	public void requestHandlingNoHandlerFound(HttpServletRequest req,HttpServletResponse resp) throws ApiMethodNotFoundException {
	   throw new ApiMethodNotFoundException(req.getMethod(), req.getRequestURI().toString());
	}
	
	@RequestMapping(value = "/handle_400")
	@ResponseBody
	public void requestHandlingBadRequest(HttpServletRequest req) throws ApiBadRequestHandlerException {
	   throw new ApiBadRequestHandlerException(req.getMethod(), req.getRequestURI().toString());
	}
	
	@RequestMapping(value = "/handle_401")
	@ResponseBody
	public void requestHandlingNotAuth(HttpServletRequest req) throws ApiNotAuthMethodHadlerException {
	   throw new ApiNotAuthMethodHadlerException(req.getMethod(), req.getRequestURI().toString());
	}
}
