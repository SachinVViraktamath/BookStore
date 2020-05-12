package com.bridgelabz.bookstore.serviceimplemantation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.dto.UserLoginDto;
import com.bridgelabz.bookstore.dto.UserPasswordDto;
import com.bridgelabz.bookstore.dto.UserRegisterDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.UserAddressRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.UserService;
import com.bridgelabz.bookstore.utility.JwtService;

@Service
public class UserAddressServiceImplementation implements UserService{

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
			add.setAddressType(addressDto.getAddressType());
			reposit.addAddress(add.getStreet(), add.getTown(), add.getDistrict(), add.getState(), add.getCountry(), add.getAddressType(),add.getPinCode());
			
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

	@Override
	public Users userRegistration(@Valid UserRegisterDto userInfoDto) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users userVerification(String token) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users userLogin(UserLoginDto login) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users forgetPassword(String email) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updatePassword(UserPasswordDto forgetpass, String token) throws UserException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Book addWishList(Long bookId, String token, String email) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getWish(String token) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book removeWishList(Long bookId, String token, String email) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
