package com.bridgelabz.bookstore.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.dto.AdimRestPassword;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.RegisterDto;
import com.bridgelabz.bookstore.dto.AdminPasswordDto;
import com.bridgelabz.bookstore.entity.Admin;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.ExceptionMessages;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.serviceimplemantation.AdminServiceImplementation;

import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

	
	@Autowired
	private AdminServiceImplementation service;
	
	
	@ApiOperation(value = "Api Registerartion Admin",response = Iterable.class)
	@PostMapping("/registration")
	public ResponseEntity<Response> registration(@Valid @RequestBody RegisterDto admiInformation) throws AdminException  {
		Admin result = service.adminRegistartion(admiInformation);
//		   if(res.hasErrors()) {
//	    	   return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE,ExceptionMessages.USER_REGISTER_STATUS_INFO,admiInformation));
//	     }
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.REGISTER_SUCCESSFULL, result));
	}
	
	@ApiOperation(value = "Api for Admin email verification",response = Iterable.class)
	@GetMapping("/restpassword")
	public ResponseEntity<Response> resetAdmin(AdimRestPassword reset) throws AdminException {
		
		boolean update = service.resetPassword(reset);	
			return ResponseEntity.ok()
					.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.AMDIN_VERIFIED_SUCCESSFULL, update));	
		
	}
	
	@ApiOperation(value = "Api for Admin email verification",response = Iterable.class)
	@GetMapping("/verifyemail/{token}")
	public ResponseEntity<Response> verficationAdmin( @PathVariable("token") String token) throws AdminException {
		
		boolean update = service.verifyAdmin(token);	
			return ResponseEntity.ok()
					.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.AMDIN_VERIFIED_SUCCESSFULL, update));	
		
	}
	
	
	@ApiOperation(value = "Api for admin login",response = Iterable.class)
	@PostMapping("/login")
	public ResponseEntity<Response> loginAdmin(@Valid @RequestBody LoginDto adminLogin,BindingResult res) throws AdminException {
		   if(res.hasErrors()) {
	    	   return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE,ExceptionMessages.USER_REGISTER_STATUS_INFO,adminLogin));
	     }
		Admin userInformation = service.loginToAdmin(adminLogin);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.LOGIN_SUCCESSFUL, userInformation));		
	}
	
	
	@ApiOperation(value = "Api forgotpassword for admin",response = Iterable.class)
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) throws AdminException {
		Admin result = service.forgetPassword(email);		
			return ResponseEntity.ok()
					.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.PASSWORD_UPDATE_SUCCESFULL, result));		
	}

	
	@ApiOperation(value = "Api for change admin password",response = Iterable.class)
	@PutMapping("/update/{token}")
	public ResponseEntity<Response> updatePassword(@PathVariable("token") String token,
			@RequestBody AdminPasswordDto update) throws AdminException {
		boolean result = service.updatepassword(update, token);	
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.PASSWORD_UPDATE_SUCCESFULL, result));
		
	}
	
	
	@ApiOperation(value = "Api for approve books from admin",response = Iterable.class)
	@PostMapping("/approvebook")
	public ResponseEntity<Response> approveTheBook(@RequestParam("bookid") Long bookId) throws BookException {
		boolean result = service.approveBook(bookId);		
			return ResponseEntity.ok()
					.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.BOOK_APPROVED, result));
		

	}
	
	@ApiOperation(value = "Api for get not approve books from admin",response = Iterable.class)
	@PostMapping("/get_not_approvebooks")
	public ResponseEntity<Response> approveTheBook(@RequestParam("bookid") String token) throws BookException, AdminException {
		List<Book> result = service.getNotapproveBook(token);					
			return ResponseEntity.ok()
					.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.BOOK_APPROVED, result));
		

	}
	
	
	
}

