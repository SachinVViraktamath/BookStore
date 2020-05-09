package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;

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
public class UserInfoDto {

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
