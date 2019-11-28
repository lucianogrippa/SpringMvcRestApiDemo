package controllers;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import models.Content;

@RestController
@RequestMapping("/api")
public class ContentTestController {
	private Logger logger = Logger.getLogger(ContentTestController.class);
	
	@RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
	public @ResponseBody Content getContent(
	@PathVariable("id") 
	long id) {
		logger.info("inside /api/test/"+id);
		
		Content content = new Content();
		content.setId(id);
		content.setDescription("prova");
		
		logger.debug("result id="+content.getId()+" description="+content.getDescription() );
		return content;
		
	}
}