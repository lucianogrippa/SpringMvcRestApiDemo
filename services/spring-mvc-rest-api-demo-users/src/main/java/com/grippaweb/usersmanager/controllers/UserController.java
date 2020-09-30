package com.grippaweb.usersmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grippaweb.usersmanager.dtos.Content;
import com.grippaweb.usersmanager.entities.User;
import com.grippaweb.usersmanager.exceptions.ApiNotAcceptedHandlerException;
import com.grippaweb.usersmanager.helpers.LogHelper;
import com.grippaweb.usersmanager.services.UserService;;

@RestController
@RequestMapping("/api/users")
public class UserController {

    LogHelper logger = new LogHelper(getClass());

    @Autowired
    UserService userService;

    @GetMapping(value = "/find/{id}")
    public @ResponseBody Content findUserById(@PathVariable(name = "id") String id)
	    throws ApiNotAcceptedHandlerException {
	logger.logInfo("calling: /listusers/ ");
	Content resp = new Content();
	resp.setId(System.currentTimeMillis());
	resp.setData(userService.listAll());
	resp.setStatus(200);
	resp.setDescription("list of all users");
	return resp;
    }

    @GetMapping(value = "/find/listusers")
    public @ResponseBody Content listusers() throws ApiNotAcceptedHandlerException {
	logger.logInfo("calling: /listusers/ ");
	Content resp = new Content();
	resp.setId(System.currentTimeMillis());
	resp.setData(userService.listAll());
	resp.setStatus(200);
	resp.setDescription("list of all users");
	return resp;
    }

    @PostMapping(value = "/saveUser")
    public @ResponseBody Content createUser(@RequestBody User user) throws ApiNotAcceptedHandlerException {
	logger.logInfo("calling: /saveUser/ ");
	Content resp = new Content();
	resp.setId(System.currentTimeMillis());
	boolean isSaved = userService.save(user);
	if (isSaved) {
	    resp.setData(true);
	    resp.setStatus(200);
	} else {
	    throw new ApiNotAcceptedHandlerException("can't save user");
	}
	return resp;
    }

    @DeleteMapping(value = "/deleteUser/{userId}")
    public @ResponseBody Content deleteUser(@PathVariable(name = "userId", required = true) long userId)
	    throws ApiNotAcceptedHandlerException {
	logger.logInfo("calling: /deleteUser/ ");
	Content resp = new Content();
	resp.setId(System.currentTimeMillis());
	boolean isSaved = userService.delete(userId);
	if (isSaved) {
	    resp.setData(true);
	    resp.setStatus(200);
	} else {
	    throw new ApiNotAcceptedHandlerException("can't save user");
	}
	return resp;
    }
}