package com.bridgelabz.bookstore.service;


import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.ResetPassword;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.Seller;


public interface SellerService {
	

	public Seller register(SellerDto dto);

	public Seller login(LoginDto login);

	public Boolean verify(String token);

	public Boolean resetPassword(ResetPassword update, String token);

	public Seller forgetPassword(String email);

	
	
}
