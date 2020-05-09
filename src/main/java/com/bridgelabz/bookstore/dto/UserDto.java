package com.bridgelabz.bookstore.dto;

import java.util.Date;

import lombok.Data;
import lombok.Getter;

@Data
@Getter

public class UserDto {
	
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String gender;
	private String phNo;
	private Date dob;
	
}
