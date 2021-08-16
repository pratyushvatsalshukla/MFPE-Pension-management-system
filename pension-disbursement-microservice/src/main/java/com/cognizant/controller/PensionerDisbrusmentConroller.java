package com.cognizant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.exception.ProcessPensionException;
import com.cognizant.model.PensionerDetail;
import com.cognizant.model.ProcessPensionInput;
import com.cognizant.model.ProcessPensionResponse;
import com.cognizant.restClient.PensionerDetailClient;
import com.cognizant.service.PensionDisbrusmentService;

@RestController
public class PensionerDisbrusmentConroller {

	private PensionerDetailClient pensionerDetailClient;

	private PensionDisbrusmentService pensionDisbrusmentService;
	
	
	@Autowired
	public PensionerDisbrusmentConroller(PensionerDetailClient pensionerDetailClient,
			PensionDisbrusmentService pensionDisbrusmentService) {

		this.pensionerDetailClient = pensionerDetailClient;
		this.pensionDisbrusmentService = pensionDisbrusmentService;
	}

	@PostMapping("/disbursePension")
	public ProcessPensionResponse getPensionDisbursement(@RequestHeader(name = "Authorization") String token,
			@RequestBody ProcessPensionInput processPensionInput) {
		ProcessPensionResponse processPensionResponseCode = null;
		try {
			processPensionResponseCode = pensionDisbrusmentService.statusCode(
					pensionerDetailClient.findByAadhaarNumber(token, processPensionInput.getAadhaarNumber()),
					processPensionInput);
		}catch(Exception e)
		{
			throw new ProcessPensionException("Pension Amoount is not correct");
		}
		return processPensionResponseCode;

	}

	@GetMapping("/details")
	public List<PensionerDetail> allDetail() {
		List<PensionerDetail> pensionerDetail = pensionerDetailClient.getAllDetail();

		return pensionerDetail;
	}

}
