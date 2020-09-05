package com.grippaweb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grippaweb.dtos.Content;
import com.grippaweb.exceptions.ApiBadRequestHandlerException;
import com.grippaweb.exceptions.ApiNotAcceptedHandlerException;
import com.grippaweb.helpers.LogHelper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class ContentDemoController {

    LogHelper logger = new LogHelper(getClass());

    @ApiOperation(value = "Methodo echo utilizzato come esempio per la creazione di un servizio rest", 
	          notes = "Il methoso restitusce il valore passato come parametro a id e deve essere di tipo long", 
	          response = Content.class, produces = "application/json")
    @ApiResponses(value = { 
	    @ApiResponse(code = 200, message = "{\r\n" + 
        	"  \"data\": \"paramiter: {id}\",\r\n" + 
        	"  \"description\": \"That's works\",\r\n" + 
        	"  \"id\": 1235566,\r\n" + 
        	"  \"status\": 200\r\n" + 
        	"}"), 
	    })
    @GetMapping(value = "/echo/{id}",produces = "application/json")
    public @ResponseBody Content getContent(@PathVariable("id") long id) throws ApiNotAcceptedHandlerException, ApiBadRequestHandlerException {
	logger.logInfo("calling: /test/ " + id);

	if(id<0)
	{
	    throw new ApiNotAcceptedHandlerException("negative numbers not found");
	}
	
	if(id>100000) {
	    throw new ApiBadRequestHandlerException("this method is avaible only for numbers < 100000");
	}
	    
	Content content = new Content();
	content.setId(id);
	content.setData("paramiter: " + id);
	content.setDescription("That's works");
	content.setStatus(200);
	content.setError(null);

	return content;

    }
}