 package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AdminDto {
	
	@NotBlank
	private String adminFirstname;
	
	@NotBlank
	private String adminLastname;
	
	@NotBlank	
	private String email;
	
	
	@NotBlank
	private String password;
	
		
	private Long adminPhoneNumber;
	

	@NotBlank
	private String adminRole;
}

