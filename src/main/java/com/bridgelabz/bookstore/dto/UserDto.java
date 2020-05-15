package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
	
	@NotNull
	@Size(min=3)
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	@Size(min=6)
	private String password;
	@NotNull
	private String email;
	@NotNull
	private String gender;
	@NotNull
	@Size(min=10,max=10)
	private long phNo;


	
}
