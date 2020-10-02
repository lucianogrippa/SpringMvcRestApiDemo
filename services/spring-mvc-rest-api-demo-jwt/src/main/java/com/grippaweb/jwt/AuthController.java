package com.grippaweb.jwt;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grippaweb.jwt.dtos.JwtTokenRequest;
import com.grippaweb.jwt.dtos.JwtTokenResponse;
import com.grippaweb.jwt.exceptions.ApiForbiddenHandlerException;
import com.grippaweb.jwt.helpers.JwtTokenHelper;
import com.grippaweb.jwt.helpers.LogHelper;

@RestController
public class AuthController {
    @Value("${sicurezza.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenUtil;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    LogHelper logger = new LogHelper(getClass());

    @PostMapping(value = "/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest) throws ApiForbiddenHandlerException {
	logger.logInfo("creating token");

	authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

	final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

	final String token = jwtTokenUtil.generateToken(userDetails);

	logger.logDebug(String.format("Token %s", token));

	return ResponseEntity.ok(new JwtTokenResponse(token));
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
	String authToken = request.getHeader(tokenHeader);
	final String token = authToken.substring(7);

	if (jwtTokenUtil.canTokenBeRefreshed(token)) {
	    String refreshedToken = jwtTokenUtil.refreshToken(token);

	    return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
	} else {
	    return ResponseEntity.badRequest().body(null);
	}
    }

    private void authenticate(String username, String password) throws ApiForbiddenHandlerException {
	Objects.requireNonNull(username);
	Objects.requireNonNull(password);

	try {
	    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	} catch (DisabledException e) {
	    logger.logException(e);
	    throw new ApiForbiddenHandlerException("User has locked account", e);
	} catch (BadCredentialsException e) {
	    logger.logException(e);
	    throw new ApiForbiddenHandlerException("bad username or password", e);
	}
    }
}
