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
import entities.Roles;
import exceptions.ApiNotAcceptedHandlerException;
import helpers.AppPropertiesHelper;
import helpers.LogHelper;
import services.RolesService;

@RestController
@RequestMapping("/api")
public class RolesController {

	@Autowired
	LogHelper logger;

	@Autowired
	AppPropertiesHelper appPropertiesHelper;
	
	@Autowired RolesService roleService;
	
	@GetMapping(value = "/listroles")
	public @ResponseBody Content listRoles() {
		Content content = new Content();
		content.setId(System.currentTimeMillis());
		content.setDescription("List of roles");
		content.setStatus(200);
		content.setData(roleService.listAll());
		return content;
	}
	
	@GetMapping(value = "/role/{roleid}")
	public @ResponseBody Content getRole(@PathVariable(value = "roleid") long roleId) {
		Content content = new Content();
		content.setId(System.currentTimeMillis());
		content.setDescription("get single row by id");
		content.setStatus(200);
		content.setData(roleService.find(roleId));
		return content;
	}
	
	@GetMapping(value = "/rolecode/{code}")
	public @ResponseBody Content rolecode(@PathVariable(name = "code") String code) {
		Content content = new Content();
		content.setId(System.currentTimeMillis());
		content.setDescription("get single row by id");
		content.setStatus(200);
		content.setData(roleService.find(code));
		return content;
	}
	
	@PostMapping(value = "/saveRole")
	public @ResponseBody Content createUser(@RequestBody Roles role) throws ApiNotAcceptedHandlerException {
		LogHelper.getLogger().logInfo("calling: /saveRole/ ");
		Content resp = new Content();
		resp.setId(System.currentTimeMillis());
		boolean isSaved = roleService.save(role);
		if (isSaved) {
			resp.setData(true);
			resp.setStatus(200);
		}
		else
		{
		   throw new ApiNotAcceptedHandlerException("can't save role");
		}
		return resp;
	}
	
	@DeleteMapping(value = "/deleteRole/{roleId}")
	public @ResponseBody Content deleteUser(@PathVariable(name = "roleId",required = true) long roleId) throws ApiNotAcceptedHandlerException {
		LogHelper.getLogger().logInfo("calling: /deleteRole/ ");
		Content resp = new Content();
		resp.setId(System.currentTimeMillis());
		boolean isSaved = roleService.remove(roleId);
		if (isSaved) {
			resp.setData(true);
			resp.setStatus(200);
		}
		else
		{
		   throw new ApiNotAcceptedHandlerException("can't remove role");
		}
		return resp;
	}
}
