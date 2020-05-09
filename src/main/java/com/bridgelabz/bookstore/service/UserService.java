package com.bridgelabz.bookstore.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.bookstore.dto.UpdateUserPassword;
import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.dto.UserLogin;
import com.bridgelabz.bookstore.entity.UserData;
import com.bridgelabz.bookstore.exception.UserNotFoundException;

public interface UserService {

	UserData userRegistration(UserDto userdto) throws UserNotFoundException;
	boolean userVerification(String token)throws UserNotFoundException;
	UserData userLogin(UserLogin login) throws UserNotFoundException;
	UserData forgetPassword(String email)throws UserNotFoundException;
	UserData updatePassword(UpdateUserPassword update,String token) throws JWTVerificationException, Exception;
}
