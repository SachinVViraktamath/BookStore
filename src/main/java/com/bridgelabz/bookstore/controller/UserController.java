package com.bridgelabz.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.bookstore.dto.UserPasswordDto;
import com.bridgelabz.bookstore.dto.UserRegisterDto;
import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.dto.UserLoginDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;


	@ApiOperation(value = "Api to Register User for BookStore", response = Response.class)
	@PostMapping("/register")
	public ResponseEntity<Response> registeration(@RequestBody UserRegisterDto userInfoDto) throws UserException {
		Users user = service.userRegistration(userInfoDto);
		if (user != null) {
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.ACCEPTED, "Registered Successfully", userInfoDto));
		}
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.NOT_ACCEPTABLE, "User Already Exist in email id", userInfoDto));

	}

	@ApiOperation(value = "Api to verify User for BookStore", response = Response.class)
	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> verification(@PathVariable("token") String token) throws UserException {
		Users user = service.userVerification(token);
		if (user != null) {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.ACCEPTED, "Successfully verified", 200));
		}
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.NOT_ACCEPTABLE, "verification failed", 400));
	}

	@ApiOperation(value = "Api to Login User for BookStore", response = Response.class)
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody UserLoginDto login) throws UserException {
		Users user = service.userLogin(login);
		if (user != null) {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.ACCEPTED, "Login Successfull", 200));
		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "Login failed Please login again", 400));

	}

	@ApiOperation(value = "Api to check if UserExists in BookStore", response = Response.class)
	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@RequestBody String email) throws UserException {
		Users user = service.forgetPassword(email);
		if (user != null) {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.ACCEPTED, " password changed Successfully ", 200));
		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "user does not exists .", 400));

	}

	@ApiOperation(value = "Api to Update User Password for BookStore", response = Response.class)
	@PutMapping("/updatePassword/{token}")
	public ResponseEntity<Response> updatePassword(@RequestBody UserPasswordDto password,
			@Valid @PathVariable("token") String token) throws UserException {
		Users user = service.userVerification(token);
		if (user != null) {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.ACCEPTED, " Successfully Updated ", 200));
		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "failed to update password", 400));

	}

	@ApiOperation(value = "Api to Add User Address for BookStore", response = Response.class)
	@PostMapping("/addAddress/create")
	public ResponseEntity<Response> addAddress(@RequestBody UserAddressDto addDto, @RequestHeader String token)
			throws UserException {
		UserAddress add = service.addAddress(addDto, token);
		if (add != null) {
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.ACCEPTED, " User Address added Successfully  ", 200));
		}

		return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "something went wrong..", 400));

	}

	@ApiOperation(value = "Api to Update User Address for BookStore", response = Response.class)
	@PutMapping("/updateAddress/{addressId}")
	public ResponseEntity<Response> updateAddress(String token, @PathVariable long addressId,
			@RequestBody UserAddressDto addDto) throws UserException {
		UserAddress add = service.updateAddress(token, addDto, addressId);
		if (add != null) {
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.ACCEPTED, " User Address Updated Successfully  ", 200));
		}

		return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "failed to update the user address", 400));

	}
	
	@ApiOperation(value = "Api to add book in WishList for BookStore", response = Response.class)
	@PostMapping("/addToWishList")
	public ResponseEntity<Response> addToWishList(@RequestParam Long bookId, @RequestHeader String token,@RequestParam String email) throws UserException
	{
		Book book = service.addWishList(bookId, token, email);
		
		if(book!=null) {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.ACCEPTED," Added To WishList",200));
		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE," Not added", 400));
	}
	
	@ApiOperation(value = "Api to remove book from WishList for BookStore", response = Response.class)
	@PostMapping("/removeFromWishList")
	public ResponseEntity<Response> removeFromWishList(@RequestParam Long bookId, @RequestHeader String token,@RequestParam String email) throws UserException
	{
		Book book = service.removeWishList(bookId, token, email);
		
		if(book!=null) {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.ACCEPTED," removed from WishList",200));
		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE," Not removed", 400));
	}
	
	
	@ApiOperation(value = "Api to get Books from wiishList for BookStore", response = Response.class)
	@GetMapping("/getAllWishList")
	public ResponseEntity<Response> getAllWishList(@RequestHeader String token) throws UserException {
		
		List<Book> user = service.getWishList(token);
		if(user!=null) {
		
		return ResponseEntity.badRequest().body(new Response(HttpStatus.ACCEPTED," WishList List is here.. ",200));

		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE,"  No books in WishList List", 400));
	}
}

