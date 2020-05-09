package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class SellerDto {
	 private String SellerName;
	    private String email;
	    private String password;
	    private String mobileNumber;

	    public String getSellerName() {
	        return SellerName;
	    }

	    public void setSellerName(String sellerName) {
	        SellerName = sellerName;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getMobileNumber() {
	        return mobileNumber;
	    }

	    public void setMobileNumber(String mobileNumber) {
	        this.mobileNumber = mobileNumber;
	    }
}
