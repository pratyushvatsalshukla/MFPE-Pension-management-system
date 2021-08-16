package com.cognizant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.model.PensionerDetail;
import com.cognizant.repository.PensionerDetailRepository;
import com.cognizant.restclient.AuthorizationClient;
import com.cognizant.service.PensionarDetailServiceImpl;

@RestController
public class PensionerDetailsController {

	private PensionerDetailRepository pensionerDetailRepository;
	private AuthorizationClient authorizationClient;
	
	

	@Autowired
	public PensionerDetailsController(PensionerDetailRepository pensionerDetailRepository,
			PensionarDetailServiceImpl pensionarDetailServiceImpl, AuthorizationClient authorizationClient) {
		this.pensionerDetailRepository = pensionerDetailRepository;
		this.authorizationClient = authorizationClient;
	}


	@GetMapping("/pensionerDetail/{aadhaarNumber}")
	public PensionerDetail findByAadhaarNumber(@RequestHeader("Authorization") String token,
			@PathVariable String aadhaarNumber) throws Exception {
		
		if (authorizationClient.authorization(token)) {

			PensionerDetail pensionerDetail = pensionerDetailRepository.findById(aadhaarNumber)
					.orElseThrow(() -> new ResourceNotFoundException("employee with aadhaar number not found"));

			return pensionerDetail;
		} else {
			throw new Exception("token is not valid");
		}
	}

	@GetMapping("/allDetails")
	public List<PensionerDetail> getAllDetail() {
		
		return pensionerDetailRepository.findAll();
	}

}
