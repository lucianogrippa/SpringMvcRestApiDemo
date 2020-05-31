package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import exceptions.ApiBadRequestHandlerException;
import exceptions.ApiForbiddenHandlerException;
import exceptions.ApiInternalServerErrorHandlerException;
import exceptions.ApiNotFoundException;
import exceptions.ApiNotAuthMethodHadlerException;
import helpers.LogHelper;

@RestController
@RequestMapping("/api")
public class HttpApiDefaultErrorPageController {
	@Autowired
	LogHelper logger;

	@RequestMapping(value = "/handle_404")
	@ResponseBody
	public void requestHandlingNoHandlerFound(HttpServletRequest req,HttpServletResponse resp) throws ApiNotFoundException {
	   throw new ApiNotFoundException(req.getMethod(), req.getRequestURI().toString());
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
	
	@RequestMapping(value = "/handle_403")
	@ResponseBody
	public void requestHandlingForbidden(HttpServletRequest req) throws ApiForbiddenHandlerException {
	   throw new ApiForbiddenHandlerException(req.getMethod(), req.getRequestURI().toString());
	}
	
	@RequestMapping(value = "/handle_500")
	@ResponseBody
	public void requestHandlingInternalServerError(HttpServletRequest req) throws ApiInternalServerErrorHandlerException {
	   throw new ApiInternalServerErrorHandlerException(req.getMethod(), req.getRequestURI().toString());
	}
}
