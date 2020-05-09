package com.bridgelabz.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.bookstore.dto.UpdateUserPassword;
import com.bridgelabz.bookstore.dto.UserInfoDto;
import com.bridgelabz.bookstore.dto.UserLogin;
import com.bridgelabz.bookstore.entity.UserData;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.UserService;
import com.bridgelabz.bookstore.utility.JwtService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private JwtService jwt; 
	
	@PostMapping("/register/")
	public ResponseEntity<Response> registeration(@RequestBody UserInfoDto UserInfoDto) throws UserNotFoundException{
		UserData user =service.userRegistration(UserInfoDto);
		
		if(user!=null) {
	
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "Registered Successfully",UserInfoDto));
		}
		
		else {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "User Already Exist",UserInfoDto));
		}
				
	}
	
	
	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> verification(@PathVariable("token") String token) throws UserNotFoundException{
			boolean user=service.userVerification(token);
		
			if (user) {
				return ResponseEntity.badRequest().body(new Response(HttpStatus.ACCEPTED,"Successfully verified", 200));
			}
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE,"user already verified..", 400));
		}
	

	@PostMapping("/login/")
	public ResponseEntity<Response> login(@RequestBody UserLogin login) throws UserNotFoundException {
		UserData user = service.userLogin(login);
		
		if(user!=null) {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.ACCEPTED,"Login Successfull",200));
		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE,"something went wrong..", 400));

	}
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@RequestBody String email ) throws UserNotFoundException {
		UserData user = service.forgetPassword(email);
		
		if(user!=null) {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.ACCEPTED," Success Login",200));
		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE,"user does not exists .", 400));

	}
	
	@PutMapping("/updatePassword")
	public ResponseEntity<Response> updatePassword(@RequestBody UpdateUserPassword password,@PathVariable("token") String token) throws JWTVerificationException, Exception {
		UserData result = service.updatePassword(password, token);

		if (result != null) {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.ACCEPTED," Successfully Updated ",200));
		} else {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE,"something went wrong..", 400));
		}
	}}
