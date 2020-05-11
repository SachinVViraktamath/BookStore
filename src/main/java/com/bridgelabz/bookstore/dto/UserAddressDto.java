package com.bridgelabz.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDto {

		private String street;
	    private String town;
	    private String district;
	    private String state;
	    private String country;
	    private int pinCode;
	
}
