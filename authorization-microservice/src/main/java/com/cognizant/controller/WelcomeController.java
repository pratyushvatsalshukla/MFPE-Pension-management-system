package com.cognizant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.exception.UserNotFound;
import com.cognizant.model.AuthRequest;
import com.cognizant.service.CustomUserDetailService;
import com.cognizant.util.JwtUtil;

@RestController
public class WelcomeController {

	private JwtUtil jwtUtil;

	private CustomUserDetailService userDetailService;

	private AuthenticationManager authenticationManager;

	@Autowired
	public WelcomeController(JwtUtil jwtUtil, CustomUserDetailService userDetailService,
			AuthenticationManager authenticationManager) {

		this.jwtUtil = jwtUtil;
		this.userDetailService = userDetailService;
		this.authenticationManager = authenticationManager;
	}
	

	@GetMapping("/")
	public String welcome() {
		return "Wecome to security application";
	}

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

		} catch (Exception e) {
			throw new UserNotFound("user not found");
		}

		return jwtUtil.generateToken(authRequest.getUserName());
	}

	@GetMapping("/authorize")
	public ResponseEntity<Object> authorization(@RequestHeader("Authorization") String token1) {

		String token = token1.substring(7);
		UserDetails user = userDetailService.loadUserByUsername(jwtUtil.extractUsername(token));

		if (jwtUtil.validateToken(token, user)) {
			System.out.println("token validated");
			return new ResponseEntity<>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}

	}

}
