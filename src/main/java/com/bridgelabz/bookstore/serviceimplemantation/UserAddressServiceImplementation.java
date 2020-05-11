package com.bridgelabz.bookstore.serviceimplemantation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.UserAddressRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.UserAddressService;
import com.bridgelabz.bookstore.utility.JwtService;

@Service
public class UserAddressServiceImplementation implements UserAddressService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserAddressRepository reposit;
	
	@Override
	public UserAddress addAddress(UserAddressDto addressDto, String token) throws UserException {
		
			long id=JwtService.parse(token);
			Users user=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
			UserAddress	add=new UserAddress();
			add.setStreet(addressDto.getStreet());
			add.setTown(addressDto.getTown());
			add.setDistrict(addressDto.getDistrict());
			add.setState(addressDto.getState());
			add.setCountry(addressDto.getCountry());
			add.setPinCode(addressDto.getPinCode());
			reposit.addAddress(add.getStreet(), add.getTown(), add.getDistrict(), add.getState(), add.getCountry(), add.getPinCode());
			
			return add;	
		
	
	}
	
	@Override
	public UserAddress updateAddress(String token,UserAddressDto addDto,long addressId) throws UserException{
		    long id =  JwtService.parse(token);
			Users user=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
			UserAddress add =reposit.findbyId(addressId).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
			reposit.updateAdd(add.getStreet(),add.getTown(),add.getDistrict(),add.getState(),add.getCountry(),add.getPinCode());
						
			return add;
				
		
	
	}

	
	
	
}
