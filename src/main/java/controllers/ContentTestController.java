package controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import models.Content;

@RestController
@RequestMapping("/api")
public class ContentTestController {
	
	@RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
	public @ResponseBody Content getContent(
	@PathVariable("id") 
	long id) {
		
		Content content = new Content();
		content.setId(id);
		content.setDescription("prova");
		return content;
		
	}
}