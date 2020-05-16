package com.bridgelabz.bookstore.service;


import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.ResetPassword;
import com.bridgelabz.bookstore.dto.RegisterDto;
import com.bridgelabz.bookstore.entity.Seller;


public interface SellerService {
	

	public Seller register(RegisterDto dto);

	public Seller login(LoginDto login);

	public Boolean verify(String token);

	public Boolean resetPassword(ResetPassword update, String token);

	public Seller forgetPassword(String email);

	
	
}
