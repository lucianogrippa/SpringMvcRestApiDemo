package com.grippaweb.usersmanager.controllers;

import java.util.List;
import java.util.NoSuchElementException;

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
import com.grippaweb.usersmanager.entities.Roles;
import com.grippaweb.usersmanager.exceptions.ApiInternalServerErrorHandlerException;
import com.grippaweb.usersmanager.exceptions.ApiNotAcceptedHandlerException;
import com.grippaweb.usersmanager.exceptions.ApiNotFoundHandlerException;
import com.grippaweb.usersmanager.helpers.LogHelper;
import com.grippaweb.usersmanager.services.RolesService;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    LogHelper logger = new LogHelper(getClass());

    @Autowired
    RolesService roleService;

    @GetMapping(value = "/find/all")
    public @ResponseBody Content listRoles() throws ApiInternalServerErrorHandlerException {
	try {
	    List<Roles> roles = roleService.listAll();
	    Content content = new Content();
	    if (roles != null && !roles.isEmpty()) {

		content.setId(System.currentTimeMillis());
		content.setDescription("List of roles");
		content.setStatus(200);
		content.setData(roles);
	    } else {
		throw new ApiNotFoundHandlerException("The role list is empy");
	    }
	    return content;
	} catch (NoSuchElementException e) {
	    throw new ApiInternalServerErrorHandlerException("The role was not found");
	} catch (Exception e) {
	    throw new ApiInternalServerErrorHandlerException(e.getMessage(), e);
	}
    }

    @GetMapping(value = "/find/{roleid}")
    public @ResponseBody Content getRole(@PathVariable(value = "roleid") long roleId)
	    throws ApiInternalServerErrorHandlerException {
	Content content = new Content();
	try {
	    Roles r = roleService.find(roleId);
	    if (r != null && r.getId() > 0) {
		content.setId(System.currentTimeMillis());
		content.setDescription("get single row by id");
		content.setStatus(200);
		content.setData(r);
	    } else {
		throw new ApiInternalServerErrorHandlerException("The role was not found");
	    }
	} catch (NoSuchElementException e) {
	    throw new ApiInternalServerErrorHandlerException("The role was not found");
	} catch (Exception e) {
	    throw new ApiInternalServerErrorHandlerException(e.getMessage(), e);
	}
	return content;
    }

    @GetMapping(value = "/find/code/{code}")
    public @ResponseBody Content rolecode(@PathVariable(name = "code") String code)
	    throws ApiInternalServerErrorHandlerException {
	Content content = new Content();
	try {
	    Roles r = roleService.find(code);
	    if (r != null && r.getId() > 0) {
		content.setId(System.currentTimeMillis());
		content.setDescription("get single row by id");
		content.setStatus(200);
		content.setData(r);
	    }
	    else
	    {
		throw new ApiInternalServerErrorHandlerException("The role was not found");
	    }
	} catch (NoSuchElementException e) {
	    throw new ApiInternalServerErrorHandlerException("The role was not found");
	} catch (Exception e) {
	    throw new ApiInternalServerErrorHandlerException(e.getMessage(), e);
	}
	return content;
    }

    @PostMapping(value = "/save")
    public @ResponseBody Content createUser(@RequestBody Roles role)
	    throws ApiNotAcceptedHandlerException, ApiInternalServerErrorHandlerException {
	logger.logInfo("calling: /saveRole/ ");
	Content resp = new Content();
	try {
	    resp.setId(System.currentTimeMillis());
	    boolean isSaved = roleService.save(role);
	    if (isSaved) {
		resp.setData(true);
		resp.setStatus(200);
	    } else {
		throw new ApiNotAcceptedHandlerException("can't save role");
	    }

	} catch (Exception e) {
	    throw new ApiInternalServerErrorHandlerException(e.getMessage(), e);
	}
	return resp;
    }

    @DeleteMapping(value = "/delete/{roleId}")
    public @ResponseBody Content deleteUser(@PathVariable(name = "roleId", required = true) long roleId)
	    throws ApiNotAcceptedHandlerException, ApiInternalServerErrorHandlerException {
	logger.logInfo("calling: /deleteRole/ ");
	Content resp = new Content();
	try {
	    
	    resp.setId(System.currentTimeMillis());
	    boolean isSaved = roleService.remove(roleId);
	    if (isSaved) {
		resp.setData(true);
		resp.setStatus(200);
	    } else {
		throw new ApiNotAcceptedHandlerException("can't remove role");
	    }
	} catch (Exception e) {
	    throw new ApiInternalServerErrorHandlerException(e.getMessage(), e);
	}
	return resp;
    }
}
