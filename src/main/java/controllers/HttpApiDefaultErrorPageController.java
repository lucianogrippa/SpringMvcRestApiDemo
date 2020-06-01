package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import exceptions.ApiBadRequestHandlerException;
import exceptions.ApiForbiddenHandlerException;
import exceptions.ApiInternalServerErrorHandlerException;
import exceptions.ApiNotAuthMethodHadlerException;
import exceptions.ApiNotFoundHandlerException;

@RestController
@RequestMapping("/api")
public class HttpApiDefaultErrorPageController extends BaseController {
	
	@RequestMapping(value = "/handle_404")
	@ResponseBody
	public void requestHandlingNoHandlerFound(HttpServletRequest req, HttpServletResponse resp)
			throws ApiNotFoundHandlerException {
		throw new ApiNotFoundHandlerException();
	}

	@RequestMapping(value = "/handle_400")
	@ResponseBody
	public void requestHandlingBadRequest(HttpServletRequest req) throws ApiBadRequestHandlerException {
		logger.logDebug("in /handle_400");
		throw new ApiBadRequestHandlerException();
	}

	@RequestMapping(value = "/handle_401")
	@ResponseBody
	public void requestHandlingNotAuth(HttpServletRequest req) throws ApiNotAuthMethodHadlerException {
		throw new ApiNotAuthMethodHadlerException();
	}

	@RequestMapping(value = "/handle_403")
	@ResponseBody
	public void requestHandlingForbidden(HttpServletRequest req) throws ApiForbiddenHandlerException {
		throw new ApiForbiddenHandlerException();
	}

	@RequestMapping(value = "/handle_500")
	@ResponseBody
	public void requestHandlingInternalServerError(HttpServletRequest req)
			throws ApiInternalServerErrorHandlerException {
		throw new ApiInternalServerErrorHandlerException();
	}
}
