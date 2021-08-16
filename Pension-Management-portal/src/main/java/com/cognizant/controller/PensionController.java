package com.cognizant.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.model.AuthRequest;
import com.cognizant.model.PensionDetail;
import com.cognizant.model.PensionerDetail;
import com.cognizant.model.PensionerInput;
import com.cognizant.model.ProcessPensionInput;
import com.cognizant.model.ProcessPensionResponse;
import com.cognizant.model.Token;
import com.cognizant.restClient.AuthorizationClient;
import com.cognizant.restClient.PensionDisbursementClient;
import com.cognizant.restClient.ProcessPensionClient;

@RestController
@CrossOrigin(origins = "*")
public class PensionController {
	
	private AuthorizationClient authorizationClient;

	private ProcessPensionClient processPensionClient;

	private PensionDisbursementClient pensionDisbursementClient;
	
	
	
	@Autowired
	public PensionController(AuthorizationClient authorizationClient, ProcessPensionClient processPensionClient,
			PensionDisbursementClient pensionDisbursementClient) {
		this.authorizationClient = authorizationClient;
		this.processPensionClient = processPensionClient;
		this.pensionDisbursementClient = pensionDisbursementClient;
	}

	@PostMapping("/token")
	public ResponseEntity<?> doLogin(@RequestBody AuthRequest authRequest)
	{
		String token=null;
		try {
			token=authorizationClient.generateToken(authRequest);
		
		} catch (Exception e) {
			
			throw new ResourceNotFoundException("token can't be generated");
		}
		
		System.out.println(token);
		
		//Map<String, String> jsonOb=new HashMap<>();
		//jsonOb.put("token", token);
		
		return ResponseEntity.ok(new Token(token));
	}
	
	@GetMapping("/details")
	public List<PensionerDetail> allDetail()
	{
		List<PensionerDetail> pensionerDetail=processPensionClient.allDetail();
		
		return pensionerDetail;
		
	}
	
	@PostMapping("/pensionDetail")
	public ResponseEntity<PensionDetail> getPensionDetail(@RequestHeader(name = "Authorization") String token,  @RequestBody PensionerInput pensionerInput)
	{
		try {
		authorizationClient.authorization(token);
		}catch (Exception e) {
			throw new ResourceNotFoundException("enter a valid token");
		}
		PensionDetail pensionDetail=processPensionClient.getPensionDetail(token, pensionerInput);
		
		return ResponseEntity.ok(pensionDetail);
		
	}
	
	@PostMapping("/processPension")
	public ProcessPensionResponse getStatusCode(@RequestHeader(name = "Authorization") String token,  @RequestBody ProcessPensionInput processPensionInput)
	{	
		try {
			authorizationClient.authorization(token);
			}catch (Exception e) {
				throw new ResourceNotFoundException("enter a valid token");
			}
		return pensionDisbursementClient.getPensionDisbursement(token, processPensionInput);
	}

	
	

}
