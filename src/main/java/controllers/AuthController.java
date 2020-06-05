package controllers;

import java.io.UnsupportedEncodingException;

import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dtos.AuthPayload;
import dtos.Content;
import dtos.JwtUser;
import entities.User;
import exceptions.ApiForbiddenHandlerException;
import exceptions.ApiNotAuthMethodHadlerException;
import helpers.JwtHelper;
import helpers.LogHelper;
import services.UserService;

@RestController
@RequestMapping("/api")
public class AuthController extends BaseController {
	@Autowired
	LogHelper logger;
	
	@Autowired
	JwtHelper jwtHelper;
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping(value = "/signin/{authToken}", method = RequestMethod.GET)
	public @ResponseBody String signinAsString(@PathVariable(value = "authToken") String authToken) throws ApiNotAuthMethodHadlerException, ApiForbiddenHandlerException {
		String resp = null;
		if (!StringUtils.isEmpty(authToken)) {
			
			// cerca l'utente
			User user = userService.authUserByRequestAuthToken(authToken);
			if(user != null) {
				JwtUser userData = JwtUser.fromUser(user);
				String token;
				try {
					try {
						token = jwtHelper.generateToken(userData);
					} catch (UnsupportedEncodingException e) {
						throw new ApiForbiddenHandlerException("unable to generate token",e);
					}
					resp=token;
				} catch (JoseException e) {
					logger.logException(e);
					throw new ApiForbiddenHandlerException("unable to generate token",e);
				}
			}
			else
			{
				throw new ApiNotAuthMethodHadlerException("error username or password");
			}
		} else {
			throw new ApiNotAuthMethodHadlerException();
		}
		return resp;
	}
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public @ResponseBody Content signin(@RequestBody AuthPayload authPayload) throws ApiNotAuthMethodHadlerException, ApiForbiddenHandlerException {
		Content resp = new Content();
		resp.setId(System.currentTimeMillis());
		resp.setData(null);
		resp.setError(null);
		resp.setStatus(200);

		if (authPayload != null && !StringUtils.isEmpty(authPayload.getAppKey())
				&& !StringUtils.isEmpty(authPayload.getUsername()) && !StringUtils.isEmpty(authPayload.getSecret())) {
			
			// cerca l'utente
			User user = userService.authUser(authPayload.getUsername(), authPayload.getSecret(), authPayload.getAppKey());
			if(user != null) {
				JwtUser userData = JwtUser.fromUser(user);
				String token;
				try {
					try {
						token = jwtHelper.generateToken(userData);
					} catch (UnsupportedEncodingException e) {
						throw new ApiForbiddenHandlerException("unable to generate token",e);
					}
					userData.setJwt(token);
					
					// ok utente autenticato restituisci l'utente con token
					resp.setData(token);
					
					
				} catch (JoseException e) {
					logger.logException(e);
					throw new ApiForbiddenHandlerException("unable to generate token",e);
				}
			}
			else
			{
				throw new ApiNotAuthMethodHadlerException("error username or password");
			}
		} else {
			throw new ApiNotAuthMethodHadlerException();
		}
		return resp;
	}
}
