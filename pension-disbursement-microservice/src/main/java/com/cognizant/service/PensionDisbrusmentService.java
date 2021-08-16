package com.cognizant.service;
import org.springframework.stereotype.Service;

import com.cognizant.model.PensionerDetail;
import com.cognizant.model.ProcessPensionInput;
import com.cognizant.model.ProcessPensionResponse;

@Service
public class PensionDisbrusmentService {

	private int successCode = 10;
	private int failedCode = 21;

	public ProcessPensionResponse statusCode(PensionerDetail pensionerDetail , ProcessPensionInput processPensionInput) {

			String bankType=pensionerDetail.getBankDetail().getBankType();
			double bankServiceCharge=processPensionInput.getBankServiceCharge();
			
			if(inputBankCharge(bankType, bankServiceCharge) &&
					calculatePension(pensionerDetail, processPensionInput.getPensionAmount()))
			{
				return new ProcessPensionResponse(successCode);
			}
	
			return new ProcessPensionResponse(failedCode);
	}
	
	public boolean calculatePension(PensionerDetail pensionerDetail, double pensionInput) {
		double pensionAmount = 0;
		if (pensionerDetail.getPensionType().equalsIgnoreCase("self")) {
			pensionAmount = 0.8 * pensionerDetail.getSalary() + pensionerDetail.getAllowance();
		} else if (pensionerDetail.getPensionType().equalsIgnoreCase("family")) {
			pensionAmount = 0.5 * pensionerDetail.getSalary() + pensionerDetail.getAllowance();
		}
		
		return pensionAmount == pensionInput;
	}
	
	public boolean inputBankCharge(String bankType, double bankCharge) {
		
		if(bankType.equalsIgnoreCase("private")) {
			if (bankCharge == 550) {
				
				return true;
			}
			else {
				return false;
			}
		}
		if(bankType.equalsIgnoreCase("public")) {
			if (bankCharge == 500)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}

	
	
	

}
