package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class AdminDto {
	
	private String adminFirstname;
	private String adminLastname;
	private String adminEmailId;
	private String adminPassword;
	private long adminPhoneNumber;		
	private String adminRole;
}

