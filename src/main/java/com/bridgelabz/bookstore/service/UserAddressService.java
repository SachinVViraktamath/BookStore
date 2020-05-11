package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.entity.UserAddress;

public interface UserAddressService {

	UserAddress addAddress( UserAddressDto addressDto, String token );
	
	//boolean removeAddress( long addressId, String token );
	
	//List<UserAddress> getAllAddress( String token );
	
	 UserAddress updateAddress(String token,UserAddressDto addDto,long addressId);
}
