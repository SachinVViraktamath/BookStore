package com.bridgelabz.bookstore.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.ResetPassword;
import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.UserException;
public interface UserService {

	public Users register(@Valid UserDto userInfoDto) throws UserException;
	Users verifyUser(String token)throws UserException;
	Users login(LoginDto login) throws UserException;
	Users forgetPassword(String email)throws UserException;
	boolean resetPassword(ResetPassword resetPassword, String token) throws UserException;
	UserAddress address( UserAddressDto addressDto, String token )throws UserException;
	UserAddress updateAddress(String token,UserAddressDto addDto,Long addressId)throws UserException;
}
