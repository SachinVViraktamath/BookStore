package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserRegisterDto {
	
	@NotNull
	private String userName;
	@NotNull
	private String userLastName;
	@NotNull
	private String password;
	@NotNull
	private String email;
	@NotNull
	private String gender;
	@NotNull
	private long phoneNumber;


	
}
