package exceptions;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import dtos.ApiErrorMessageResponse;
import helpers.ErrorHandlerHelper;
import helpers.LogHelper;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiBadRequestHandlerException extends ServletException {
	private static final long serialVersionUID = 1L;
    
	LogHelper logger = LogHelper.getLogger();
	
	public ApiBadRequestHandlerException(){
		super("Error "+HttpStatus.BAD_REQUEST.value()+": Bad request");
	}
	public ApiBadRequestHandlerException(String message) {
		super(message);
		logger.logDebug("in ApiBadRequestHandlerException "+message);
	}
	
	public ApiBadRequestHandlerException(String message,Throwable exc) {
		super(message,exc);
		logger.logDebug("in ApiBadRequestHandlerException "+message);
	}

	public ApiErrorMessageResponse getResponseDto() {
		
		ApiErrorMessageResponse errorInfo = new ApiErrorMessageResponse();
		String description = "";
		if(getRootCause()!= null) {
			description =  ErrorHandlerHelper.getStackTrace(getRootCause());
		}
		else if(getMessage() != null && !getMessage().isEmpty())
		{
			description = getMessage();
		}
		else
		{
			description ="Bad request error 400 ";
		}
		
		errorInfo.setExeption(getRootCause());
		
		errorInfo.setData(null);
		errorInfo.setDescription(description);
		errorInfo.setError("API_BAD_REQUEST");
		errorInfo.setId(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.BAD_REQUEST.value());
		
		return errorInfo;
	}
}
