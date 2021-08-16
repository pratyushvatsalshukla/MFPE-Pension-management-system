package com.cognizant.model;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PensionDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String dateOfBirth;
	private String panNumber;
	private String pensiontype;
	private double pensionAmount;
	
	
	public PensionDetail(String name, String dateOfBirth, String panNumber, String pensiontype, double pensionAmount) {
		
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.panNumber = panNumber;
		this.pensiontype = pensiontype;
		this.pensionAmount = pensionAmount;
	}
	
	

}
