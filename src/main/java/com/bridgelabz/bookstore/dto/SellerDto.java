package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotBlank;
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
	@NotBlank
	private String SellerName;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private Long mobileNumber;
	

}
