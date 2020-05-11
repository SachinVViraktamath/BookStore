package com.bridgelabz.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDto {
	
	private String newPassword;
	private String cnfPassword;
	private String oldPassword;
}
