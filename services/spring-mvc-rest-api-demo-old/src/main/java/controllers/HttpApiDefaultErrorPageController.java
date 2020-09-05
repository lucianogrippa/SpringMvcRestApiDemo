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
import exceptions.ApiMethodNotAllowedHandlerException;
import exceptions.ApiNotAcceptedHandlerException;
import exceptions.ApiNotAuthMethodHadlerException;
import exceptions.ApiNotFoundHandlerException;
import helpers.LogHelper;

@RestController
@RequestMapping("/api")
public class HttpApiDefaultErrorPageController {
	@Autowired
	LogHelper logger;
	
	@RequestMapping(value = "/handle_404")
	@ResponseBody
	public void requestHandlingNoHandlerFound(HttpServletRequest req, HttpServletResponse resp)
			throws ApiNotFoundHandlerException {
		String origMessage = (String)req.getAttribute("javax.servlet.error.message");
		throw new ApiNotFoundHandlerException(origMessage);
	}

	@RequestMapping(value = "/handle_400")
	@ResponseBody
	public void requestHandlingBadRequest(HttpServletRequest req) throws ApiBadRequestHandlerException {
		String origMessage = (String)req.getAttribute("javax.servlet.error.message");
		logger.logDebug("in /handle_400");
		throw new ApiBadRequestHandlerException(origMessage);
	}

	@RequestMapping(value = "/handle_401")
	@ResponseBody
	public void requestHandlingNotAuth(HttpServletRequest req) throws ApiNotAuthMethodHadlerException {
		String origMessage = (String)req.getAttribute("javax.servlet.error.message");
		throw new ApiNotAuthMethodHadlerException(origMessage);
	}

	@RequestMapping(value = "/handle_403")
	@ResponseBody
	public void requestHandlingForbidden(HttpServletRequest req) throws ApiForbiddenHandlerException {
		String origMessage = (String)req.getAttribute("javax.servlet.error.message");
		throw new ApiForbiddenHandlerException(origMessage);
	}
	
	@RequestMapping(value = "/handle_406")
	@ResponseBody
	public void requestHandlingNotAccettable(HttpServletRequest req) throws ApiNotAcceptedHandlerException {
		String origMessage = (String)req.getAttribute("javax.servlet.error.message");
		throw new ApiNotAcceptedHandlerException(origMessage); 
	}
	
	@RequestMapping(value = "/handle_405")
	@ResponseBody
	public void requestMethodNotAllowed(HttpServletRequest req) throws ApiMethodNotAllowedHandlerException {
		String origMessage = (String)req.getAttribute("javax.servlet.error.message");
		throw new ApiMethodNotAllowedHandlerException(origMessage); 
	}

	@RequestMapping(value = "/handle_500")
	@ResponseBody
	public void requestHandlingInternalServerError(HttpServletRequest req)
			throws ApiInternalServerErrorHandlerException {
		String origMessage = (String)req.getAttribute("javax.servlet.error.message");
		throw new ApiInternalServerErrorHandlerException(origMessage);
	}
}
