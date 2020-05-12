package com.bridgelabz.bookstore.dto;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class SellerDto {
	@NotNull
	private String SellerName;
	@NotNull
	private String email;
	@NotNull
	private String password;
	@NotNull
	private String mobileNumber;
	

}
