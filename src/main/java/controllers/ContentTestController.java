package controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dtos.Content;
import entities.User;
import exceptions.ApiNotAcceptedHandlerException;
import helpers.AppPropertiesHelper;
import helpers.LogHelper;
import services.UserService;

@RestController
@RequestMapping("/api")
public class ContentTestController extends BaseController {
	@Autowired
	AppPropertiesHelper appPropertiesHelper;

	@Autowired
	UserService userService;

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

	@RequestMapping(value = "/testtoken", method = RequestMethod.GET)
	public @ResponseBody String testtoken() throws ApiNotAcceptedHandlerException {
		LogHelper.getLogger().logInfo("calling: /testtoken/ ");

		long testId = appPropertiesHelper.getAppUserId();
		User user = userService.find(testId);
		if (user != null) {
			String dbUsername = user.getUsername();
			String dbPwd = user.getSecret();
			String dbAppKey = appPropertiesHelper.getAppKey();
			String cleanToken = String.format("%s@%s@%s", dbUsername, dbPwd, dbAppKey);
			String token = DigestUtils.sha256Hex(cleanToken);

			return token;
		}

		throw new ApiNotAcceptedHandlerException("test user not found !!");
	}

}