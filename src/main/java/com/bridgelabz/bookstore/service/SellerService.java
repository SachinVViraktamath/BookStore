package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.SellerLoginDto;
import com.bridgelabz.bookstore.dto.SellerPasswordUpdateDto;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.Seller;

public interface SellerService {
	
public Seller register(SellerDto dto);
public Seller login(SellerLoginDto login);
public Boolean verify(String token);
public List<Seller> getSellers();
 public boolean addBookBySeller(String token,BookDto dto );
 public Boolean bookVerify(String token,Long bookId);
 public Boolean updatePassword(SellerPasswordUpdateDto update, String token);
 //public SellerEntity forgetPassword(String Email);
}
