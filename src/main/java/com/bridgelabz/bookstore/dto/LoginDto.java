package com.bridgelabz.bookstore.dto;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginDto {
	@NotNull
	private String email;
	@NotNull
	private String password;

}
