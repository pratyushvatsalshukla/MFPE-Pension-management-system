package com.cognizant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.model.PensionDetail;
import com.cognizant.model.PensionerDetail;
import com.cognizant.model.PensionerInput;
import com.cognizant.model.ProcessPensionResponse;
import com.cognizant.repository.PensionDetailRepository;

@Service
public class ProcessPensionService {
	
	private PensionDetailRepository pensionDetailRepository;
	
	
	@Autowired
	public ProcessPensionService(PensionDetailRepository pensionDetailRepository) {
		this.pensionDetailRepository = pensionDetailRepository;
	}



	public PensionDetail getPensionDetail(PensionerDetail pensionerDetail, PensionerInput  pensionerInput)
	{
		double pensionAmount=0;
		
		ProcessPensionResponse processPensionResponse=new ProcessPensionResponse();
		
		
		
		if(pensionerDetail.getPensionType().equalsIgnoreCase("self"))
		{
			pensionAmount=(0.8*pensionerDetail.getSalary()+pensionerDetail.getAllowance());
		}else if(pensionerDetail.getPensionType().equalsIgnoreCase("family"))
		{
			pensionAmount=(0.5*pensionerDetail.getSalary()+pensionerDetail.getAllowance());
		}
		
		
		
		
		if(pensionerDetail.getBankDetail().getBankType().equalsIgnoreCase("private"))
		{
			pensionAmount=pensionAmount-550;
			
			processPensionResponse.setProcessPensionStatusCode(10);
			
			
		}else if(pensionerDetail.getBankDetail().getBankType().equalsIgnoreCase("public"))
		{
			pensionAmount=pensionAmount-500;
			processPensionResponse.setProcessPensionStatusCode(10);
			
		}
		
		PensionDetail pensionDetail=new PensionDetail(
				pensionerDetail.getName(),pensionerDetail.getDateOfBirth(),pensionerDetail.getPanNumber(),
				pensionerDetail.getPensionType(),pensionAmount);
		
		return pensionDetail;
		
	}
	
	

	public PensionDetail savePensionDetail(PensionDetail pensionDetail) {
		return pensionDetailRepository.save(pensionDetail);

	}

	

}
