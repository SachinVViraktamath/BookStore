package com.bridgelabz.bookstore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Table(name = "userAddress")
@Entity
public class UserAddress {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long addressId;
	 
	 
	 private String street;
	 
	
	 private String town;
	
	 private String district;
	 
	 
	 private String state;
	 
	
	 private String country;
	 
	 
	 private String addressType;
	 
	 
	 private int pinCode;

	 
	
}
