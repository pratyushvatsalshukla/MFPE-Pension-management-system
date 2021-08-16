package com.cognizant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.exception.ProcessPensionException;
import com.cognizant.model.PensionDetail;
import com.cognizant.model.PensionerDetail;
import com.cognizant.model.PensionerInput;
import com.cognizant.model.ProcessPensionInput;
import com.cognizant.model.ProcessPensionResponse;
import com.cognizant.restClient.PensionDisbursementClient;
import com.cognizant.restClient.PensionerDetailClient;
import com.cognizant.service.ProcessPensionService;

@RestController
public class ProcessPensionController {

	private PensionerDetailClient pensionerDetailClient;
	private ProcessPensionService processPensionService;
	private PensionDisbursementClient pensionDisbursementClient;
	
	
	@Autowired
	public ProcessPensionController(PensionerDetailClient pensionerDetailClient,
			PensionDisbursementClient pensionDisbursementClient, ProcessPensionService processPensionService) {
		this.pensionerDetailClient = pensionerDetailClient;
		this.pensionDisbursementClient = pensionDisbursementClient;
		this.processPensionService = processPensionService;
	}

	@GetMapping("/details")
	public List<PensionerDetail> allDetail()
	{
		List<PensionerDetail> pensionerDetail=pensionerDetailClient.getAllDetail();
		
		return pensionerDetail;
	}
	
	@PostMapping("/pensionerInput")
	public PensionDetail getPensionDetail(@RequestHeader(name = "Authorization") String token, @RequestBody PensionerInput pensionerInput)
	{
		PensionDetail pensionDetail=null;
		try
		{
			pensionDetail=processPensionService.getPensionDetail(
				pensionerDetailClient.findByAadhaarNumber(token,pensionerInput.getAadhaarNumber()), pensionerInput);
		
		}catch (Exception e) {
			throw new ProcessPensionException("Pensioner Detail not coreect");
		}
		
		return processPensionService.savePensionDetail(pensionDetail);
		
		
	}
	
	@PostMapping("/processPension")
	public ProcessPensionResponse getStatusCode(@RequestHeader(name = "Authorization") String token,  @RequestBody ProcessPensionInput processPensionInput)
	{	
		return pensionDisbursementClient.getPensionDisbursement(token, processPensionInput);
	}

}
