package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AdminPasswordDto {

	
	
	@NotNull(message = "oldpassword field can't be empty!!!")	
	@Size(min =6 )
	private String Oldpassword;
	
	@NotNull(message = "new password field can't be empty!!!")	
	@Size(min =6 )
	private String newPassword;
	
	@NotNull(message = "confirm password field can't be empty!!!")	
	@Size(min =6 )
	private String confirmPassword;
	
	

	
}
