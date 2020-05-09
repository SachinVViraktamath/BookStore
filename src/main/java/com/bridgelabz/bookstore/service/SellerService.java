package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.SellerEntity;

public interface SellerService {
	
public Boolean register(SellerDto dto);
public SellerEntity login(LoginDto login);
public Boolean verify(String token);
public List<SellerEntity> getSellers();
 
}
