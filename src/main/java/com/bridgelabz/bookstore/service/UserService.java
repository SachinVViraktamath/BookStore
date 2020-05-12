package com.bridgelabz.bookstore.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.bookstore.dto.UserPasswordDto;
import com.bridgelabz.bookstore.dto.UserRegisterDto;
import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.dto.UserLoginDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.UserException;

public interface UserService {

	Users userRegistration(@Valid UserRegisterDto userInfoDto) throws UserException;
	Users userVerification(String token)throws UserException;
	Users userLogin(UserLoginDto login) throws UserException;
	Users forgetPassword(String email)throws UserException;
	boolean updatePassword(UserPasswordDto forgetpass, String token) throws UserException;
	Book addWishList(Long bookId,String token,String email) throws UserException;
	List<Book> getWishList(String token) throws UserException;
	Book removeWishList(Long bookId,String token,String email) throws UserException;
	UserAddress addAddress( UserAddressDto addressDto, String token )throws UserException;
	UserAddress updateAddress(String token,UserAddressDto addDto,long addressId)throws UserException;

}
