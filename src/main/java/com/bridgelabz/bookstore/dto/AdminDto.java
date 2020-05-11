package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class AdminDto {
	
	private String adminName;
	private String adminLastname;
	private String adminEmailId;
	private String adminPassword;
	private String adminConfirmPassword;
	private long adminPhoneNumber;		
	private boolean adminIsVerified;
	private String adminRole;
}

