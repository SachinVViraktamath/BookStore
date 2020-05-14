package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginDto {

	
	@NotNull(message = "email field can't be empty!!!")	
	private String email;
	
	@NotNull(message = "password field can't be empty!!!")	
	@Size(min = 6)
	private String password;
	

	
}
