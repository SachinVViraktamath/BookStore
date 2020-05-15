package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AdminDto {
	
	@NotNull(message = "Name field can't be empty!!!")
	@Pattern(regexp = "^[a-zA-Z]*", message = "Digits and Special characters are not allowed admin first Name field")
	@Size(min = 3)
	private String adminFirstname;
	
	@NotNull(message = "Name field can't be empty!!!")
	@Pattern(regexp = "^[a-zA-Z]*", message = "Digits and Special characters are not allowed last Name field")
	@Size(min = 3)
	private String adminLastname;
	
	@NotNull(message = "Name field can't be empty!!!")	
	private String email;
	
	
	@NotNull(message = "Name field can't be empty!!!")	
	@Size(min =6 )
	private String password;
	
	@NotNull(message = "Name field can't be empty!!!")
	@Pattern(regexp = "^[0-9]*", message = "only digit are allowed phone number field field")	
	private long adminPhoneNumber;
	

	@NotNull(message = "Name field can't be empty!!!")
	private String adminRole;
}

