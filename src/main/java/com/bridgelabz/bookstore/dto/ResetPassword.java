package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ResetPassword {

	
	@NotNull(message = "password field can't be empty!!!")	
	@Size(min = 6)
	private String confirmPassword;
	@NotNull(message = "newpassword field can't be empty!!!")	
	@Size(min = 6)
	private String newPassword;

}
