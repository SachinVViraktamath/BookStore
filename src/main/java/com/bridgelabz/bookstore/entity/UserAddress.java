package com.bridgelabz.bookstore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Table(name = "user_address")
@Entity
public class UserAddress {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long addressId;
	 
	 @NotBlank(message = "Street name is mandatory")
	 private String street;
	 
	 @NotBlank(message = "Town name is mandatory")
	 private String town;
	 
	 @NotBlank(message = "District name is mandatory")
	 private String district;
	 
	 @NotBlank(message = "State name is mandatory")
	 private String state;
	 
	 @NotBlank(message = "Country name is mandatory")
	 private String country;
	 
	 @NotBlank(message = "Pin code is mandatory")
	 private int pinCode;

	
}
