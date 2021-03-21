package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class UserController {
	@Autowired
	LogHelper logger;

	@Autowired
	AppPropertiesHelper appPropertiesHelper;

	@Autowired
	UserService userService;

	@PostMapping(value = "/saveUser")
	public @ResponseBody Content createUser(@RequestBody User user) throws ApiNotAcceptedHandlerException {
		LogHelper.getLogger().logInfo("calling: /saveUser/ ");
		Content resp = new Content();
		resp.setId(System.currentTimeMillis());
		boolean isSaved = userService.save(user);
		if (isSaved) {
			resp.setData(true);
			resp.setStatus(200);
		}
		else
		{
		   throw new ApiNotAcceptedHandlerException("can't save user");
		}
		return resp;
	}
	
	@DeleteMapping(value = "/deleteUser/{userId}")
	public @ResponseBody Content deleteUser(@PathVariable(name = "userId",required = true) long userId) throws ApiNotAcceptedHandlerException {
		LogHelper.getLogger().logInfo("calling: /deleteUser/ ");
		Content resp = new Content();
		resp.setId(System.currentTimeMillis());
		boolean isSaved = userService.delete(userId);
		if (isSaved) {
			resp.setData(true);
			resp.setStatus(200);
		}
		else
		{
		   throw new ApiNotAcceptedHandlerException("can't save user");
		}
		return resp;
	}
	
	@GetMapping(value = "/listusers")
	public @ResponseBody Content listusers() throws ApiNotAcceptedHandlerException {
		LogHelper.getLogger().logInfo("calling: /listusers/ ");
		Content resp = new Content();
		resp.setId(System.currentTimeMillis());
		resp.setData(userService.listAll());
		resp.setStatus(200);
		resp.setDescription("list of all users");
		return resp;
	}
}