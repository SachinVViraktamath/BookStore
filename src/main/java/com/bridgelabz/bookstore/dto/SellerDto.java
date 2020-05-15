package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.Size;

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
	@Size(min=3)
	private String SellerName;
	@NotNull
	private String email;
	@NotNull
	//@Size(min=6)
	private String password;
	@NotNull
	//@Size(min=10,max=10)
	private int mobileNumber;
	

}
