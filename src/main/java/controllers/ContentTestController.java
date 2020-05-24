package controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dtos.Content;
import helpers.LogHelper;

@RestController
@RequestMapping("/api")
public class ContentTestController {
	/**
	 * Simple test rest method
	 * 
	 * @param id paramiter id example
	 * @return
	 */
	@RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
	public @ResponseBody Content getContent(@PathVariable("id") long id) {
		LogHelper.getLogger().logInfo("calling: /test/ " + id);

		Content content = new Content();
		content.setId(id);
		content.setData("paramiter: " + id);
		content.setDescription("That's works");
		content.setStatus(200);
		content.setError(null);

		return content;

	}
}