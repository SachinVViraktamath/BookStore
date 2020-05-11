package com.bridgelabz.bookstore.serviceimplemantation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
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
	public UserAddress addAddress(UserAddressDto addressDto, String token) {
		try {
			long id=JwtService.parse(token);
			Users user=repository.findbyId(id);
			System.out.println(user);
			
			if(user != null) {
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
				
		return null;
	}
	
	@Override
	public UserAddress updateAddress(String token,UserAddressDto addDto,long addressId){
		try {
			
			long id =  JwtService.parse(token);
			Users user=repository.findbyId(id);
			UserAddress add =reposit.findbyId(addressId);
			
			if(user!=null) {
				
				if(add!=null) {
						
						reposit.updateAdd(add.getStreet(),add.getTown(),add.getDistrict(),add.getState(),add.getCountry(),add.getPinCode());
						return add;
				}
				return null;
			}else {
				return null;
			}
			
		}catch(Exception e) {
			System.out.println(e);
			
		}
		
		return null;
	}

	
	
	
}
