package com.bridgelabz.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	
	
	private String userName;
	private String userLastName;
	private String password;
	private String email;
	private String gender;
	private long phoneNumber;
	
	
	
}
