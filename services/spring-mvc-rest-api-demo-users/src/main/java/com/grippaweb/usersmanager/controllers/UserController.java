package com.grippaweb.usersmanager.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grippaweb.usersmanager.dtos.Content;
import com.grippaweb.usersmanager.entities.Roles;
import com.grippaweb.usersmanager.entities.User;
import com.grippaweb.usersmanager.exceptions.ApiForbiddenHandlerException;
import com.grippaweb.usersmanager.exceptions.ApiMethodNotAllowedHandlerException;
import com.grippaweb.usersmanager.exceptions.ApiNotAcceptedHandlerException;
import com.grippaweb.usersmanager.exceptions.ApiNotFoundHandlerException;
import com.grippaweb.usersmanager.helpers.LogHelper;
import com.grippaweb.usersmanager.services.RolesService;
import com.grippaweb.usersmanager.services.UserService;

import io.jsonwebtoken.ExpiredJwtException;;

@RestController
@RequestMapping("/api/users")
public class UserController {

    LogHelper logger = new LogHelper(getClass());

    @Autowired
    UserService userService;

    @Autowired
    RolesService rolesService;

    @GetMapping(value = "/find/{id}")
    public @ResponseBody Content findUserById(@PathVariable(name = "id") Long id)
	    throws ApiNotAcceptedHandlerException, ApiNotFoundHandlerException {
	logger.logInfo("calling: /find/" + id);
	Content resp = new Content();
	resp.setId(System.currentTimeMillis());
	try {
	    User user = userService.find(id);
	    if (user != null) {
		resp.setData(user);
		resp.setStatus(200);
		resp.setDescription("find user by provided id");
	    } else {
		ApiNotFoundHandlerException notFound = new ApiNotFoundHandlerException(
			"L'utente non e' stato trovato nel sistema");
		throw notFound;
	    }
	} catch (NoSuchElementException e) {
	    ApiNotFoundHandlerException notFound = new ApiNotFoundHandlerException(
		    "L'utente non e' stato trovato nel sistema");
	    notFound.setStackTrace(e.getStackTrace());
	    throw notFound;
	}
	return resp;
    }

    @GetMapping(value = "/find/uid/{username}")
    public @ResponseBody Content findUserByUsername(@PathVariable(name = "username") String username)
	    throws ApiNotAcceptedHandlerException, ApiNotFoundHandlerException {
	logger.logInfo("calling: /find/uid/ " + username);
	Content resp = new Content();
	resp.setId(System.currentTimeMillis());
	try {
	    User user = userService.findByUsername(username);
	    if (user != null) {
		resp.setData(user);
		resp.setStatus(200);
		resp.setDescription("find user by provided username");
	    } else {
		ApiNotFoundHandlerException notFound = new ApiNotFoundHandlerException(
			"L'utente non e' stato trovato nel sistema");
		throw notFound;
	    }
	} catch (NoSuchElementException e) {
	    ApiNotFoundHandlerException notFound = new ApiNotFoundHandlerException(
		    "L'utente non e' stato trovato nel sistema");
	    notFound.setStackTrace(e.getStackTrace());
	    throw notFound;
	}
	return resp;
    }

    @PostMapping(value = "/find/token")
    public @ResponseBody Content findUserByToken(@RequestBody String token) throws ApiNotAcceptedHandlerException,
	    ApiMethodNotAllowedHandlerException, ApiNotFoundHandlerException, ApiForbiddenHandlerException {
	logger.logInfo("calling: /find/token/ " + token);
	Content resp = new Content();
	resp.setId(System.currentTimeMillis());
	try {
	    if (!StringUtils.isEmpty(token)) {
		resp.setData(userService.findByToken(token));
		resp.setStatus(200);
		resp.setDescription("find by provided token");
	    } else {
		throw new ApiMethodNotAllowedHandlerException("missing parameter token");
	    }
	} catch (ExpiredJwtException e) {
	    ApiForbiddenHandlerException tokenAwt = new ApiForbiddenHandlerException(e.getMessage());
	    tokenAwt.setStackTrace(e.getStackTrace());

	    throw tokenAwt;
	} catch (NoSuchElementException e) {
	    ApiNotFoundHandlerException notFound = new ApiNotFoundHandlerException(
		    "L'utente non e' stato trovato nel sistema");
	    notFound.setStackTrace(e.getStackTrace());
	    throw notFound;
	}
	return resp;
    }

    @GetMapping(value = {"/find/all",
	    		 "/find/all/{name}",
	    		 "/find/all/start-index/{index}/max-page/{maxrow}",
	    		"/find/all/start-index/{index}/max-page/{maxrow}/name/{name}"})
    public @ResponseBody Content listusers(@PathVariable(name = "name",required = false) Optional<String> name,@PathVariable(name = "index",required = false) Optional<Integer> startIndex,@PathVariable(name = "maxrow",required = false) Optional<Integer> maxPageRow) throws ApiNotAcceptedHandlerException, ApiNotFoundHandlerException {
	String textSearch = "";
	Integer indexRow = 0;
	Integer maxRow = 10000;
	
	if(name.isPresent() && !name.isEmpty()) {
	    textSearch = name.get();
	}
	
	if(startIndex.isPresent()) {
	    indexRow= startIndex.get();
	}
	
	if(maxPageRow.isPresent()) {
	    maxRow = maxPageRow.get();
	}
	
	logger.logInfo("calling: /listusers/ ");
	
	logger.logDebug("name "+textSearch);
	
	Content resp = new Content();
	resp.setId(System.currentTimeMillis());
	try {
	    
	    List<User> users = userService.listAll(textSearch,indexRow,maxRow);
	    
	    if (users != null && users.size() > 0) {
		resp.setData(users);
		resp.setStatus(200);
		resp.setDescription("list of all users");
	    } else {
		throw new  ApiNotFoundHandlerException("La lista degli utenti e' vuota");
	    }
	} catch (NoSuchElementException e) {
	    ApiNotFoundHandlerException notFound = new ApiNotFoundHandlerException("La lista degli utenti e' vuota");
	    notFound.setStackTrace(e.getStackTrace());
	    throw notFound;
	}
	return resp;
    }

    @GetMapping(value = "/find/all/role/{role}")
    public @ResponseBody Content findAllInRole(@PathVariable("role") String role)
	    throws ApiNotAcceptedHandlerException, ApiNotFoundHandlerException {
	logger.logInfo("calling: /find/all/role/{" + role + "} ");
	Content resp = new Content();
	resp.setId(System.currentTimeMillis());
	if (!StringUtils.isEmpty(role)) {
	    Roles roleObj = rolesService.find(role);
	    if (roleObj != null) {
		resp.setData(userService.listByRole(roleObj));
		resp.setStatus(200);
		resp.setDescription("list of all users in role " + role);
	    } else {
		throw new ApiNotFoundHandlerException("role " + role + " not found in system");
	    }
	} else {
	    throw new ApiNotAcceptedHandlerException("missing parameter role");
	}
	return resp;
    }

    @PostMapping(value = "/save")
    public @ResponseBody Content createUser(@RequestBody User user) throws ApiNotAcceptedHandlerException {
	logger.logInfo("calling: /users/save/ ");
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

    @DeleteMapping(value = "/delete/{userId}")
    public @ResponseBody Content deleteUser(@PathVariable(name = "userId", required = true) long userId)
	    throws ApiNotAcceptedHandlerException {
	logger.logInfo("calling: /delete/ ");
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