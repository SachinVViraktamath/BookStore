package com.bridgelabz.bookstore.service;

import javax.validation.Valid;

import com.bridgelabz.bookstore.dto.UserPasswordDto;
import com.bridgelabz.bookstore.dto.UserRegisterDto;
import com.bridgelabz.bookstore.dto.UserLoginDto;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.UserException;

public interface UserService {


	Users userRegistration(@Valid UserRegisterDto userInfoDto) throws UserException;
	Users userVerification(String token)throws UserException;
	Users userLogin(UserLoginDto login) throws UserException;
	Users forgetPassword(String email)throws UserException;
	boolean updatePassword(UserPasswordDto forgetpass, String token) throws UserException;

}
