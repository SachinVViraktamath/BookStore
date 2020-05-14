package com.bridgelabz.bookstore.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.ResetPassword;
import com.bridgelabz.bookstore.dto.SellerPasswordUpdateDto;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.Seller;
import com.bridgelabz.bookstore.exception.SellerException;

public interface SellerService {
	

	public Seller register(SellerDto dto);

	public Seller login(LoginDto login);

	public Boolean verify(String token);

	public Boolean resetPassword(ResetPassword update, String token);

	public Seller forgetPassword(String email);

	
	
}
