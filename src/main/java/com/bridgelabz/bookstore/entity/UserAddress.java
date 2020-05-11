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

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}
	 
	 
	 


}
