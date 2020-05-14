package com.bridgelabz.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.ResetPassword;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;


	@ApiOperation(value = "Api to Register User  ", response = Response.class)
	@PostMapping("/register")
	public ResponseEntity<Response> registration(@Valid @RequestBody UserDto userInfoDto) throws UserException {
		service.register(userInfoDto);
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "User registration Successfull", userInfoDto));
	}	

	@ApiOperation(value = "Api to verify the User ", response = Response.class)
	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> verification(@PathVariable("token") String token) throws UserException {
		service.verifyUser(token);
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "User verification Successfull ", 200));
	}

	@ApiOperation(value = "Api to Login User", response = Response.class)
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDto) throws UserException {
		service.login(loginDto);
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "User login Successfull", 200));
	}

	@ApiOperation(value = "Api to check if UserExists or not", response = Response.class)
	@PostMapping("/forget-password")
	public ResponseEntity<Response> forgetPassword(@RequestParam String email) throws UserException {
			service.forgetPassword(email);
			return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "User password changed Successfully ", 200));

	}

	@ApiOperation(value = "Api to Reset User Password ", response = Response.class)
	@PutMapping("/reset-password/{token}")
	public ResponseEntity<Response> resetPassword(@RequestBody ResetPassword password,@Valid @PathVariable("token") String token) throws UserException {
			service.verifyUser(token);
			return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, " User reset password is Successfull  ", 200));
	}

	@ApiOperation(value = "Api to Add User Address", response = Response.class)
	@PostMapping("/address")
	public ResponseEntity<Response> address(@RequestBody UserAddressDto addDto, @RequestHeader String token)
			throws UserException {
		service.address(addDto, token);
		
			return ResponseEntity.ok()
					.body(new Response(HttpStatus.ACCEPTED, " User address added Successfully  ", 200));
	}

	@ApiOperation(value = "Api to Update User Address", response = Response.class)
	@PutMapping("/update-address/{addressId}")
	public ResponseEntity<Response> updateAddress(@RequestParam String token, @PathVariable long addressId,
			@RequestBody UserAddressDto addDto) throws UserException {
			service.updateAddress(token, addDto, addressId);
			return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, " User updated address Successfully  ", 200));
		
	}
	
}

