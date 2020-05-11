package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.PasswordUpdate;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.Seller;

public interface SellerService {
	
public Boolean register(SellerDto dto);
public Seller login(LoginDto login);
public Boolean verify(String token);
public List<Seller> getSellers();
 public Seller getSellerFromToken(String token);
 public boolean addBookBySeller(String token,BookDto dto );
 public Boolean bookVerify(String token,Long bookId);
 public Boolean updatePassword(PasswordUpdate update, String token);
 //public SellerEntity forgetPassword(String Email);
}
