package com.bridgelabz.bookstore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.bookstore.dto.AdminDto;
import com.bridgelabz.bookstore.dto.AdminLoginDto;
import com.bridgelabz.bookstore.dto.AdminPasswordDto;
import com.bridgelabz.bookstore.entity.Admin;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.serviceimplemantation.AdminServiceImplementation;



@RestController
public class AdminController {

	
	@Autowired
	private AdminServiceImplementation service;
	
	
	/**
	 * API used to Registration
	 * 
	 * @param AdminInformation object as input 
	 * @return Admin details
	 * @apiNote API for Registration
	 */ 
	@PostMapping("/admin/registration")
	public ResponseEntity<Response> registration(@RequestBody AdminDto admiInformation) throws AdminException {
		Admin result = service.adminRegistartion(admiInformation);
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.ACCEPTED, "Registration Successfull", result));
	}
	
	
	@GetMapping("/admin/verify/{token}")
	public ResponseEntity<Response> verficationAdmin(@PathVariable("token") String token) throws AdminException {
		boolean update = service.verifyAdmin(token);
		if (update) {
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.ACCEPTED, "veified Successfull", update));	
		}return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.NOT_ACCEPTABLE, "verified not Successfull", update));
		
	}
	
	
	@PostMapping("/admin/login")
	public ResponseEntity<Response> loginAdmin(@RequestBody AdminLoginDto adminLogin) throws AdminException {
		Admin userInformation = service.loginToAdmin(adminLogin);
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.ACCEPTED, "Login Successfull", userInformation));		
	}
	
	
	
	@PostMapping("/admin/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) throws AdminException {
		Admin result = service.isAdminExist(email);
		if (result!=null) {
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.ACCEPTED, "Admin existed", result));
		}
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.NOT_FOUND, "admin does not exit in the given email id", result));

	}

	/**
	 * API used for the update
	 *
	 * 
	 */
	@PutMapping("/admin/update/{token}")
	public ResponseEntity<Response> updatePassword(@PathVariable("token") String token,
			@RequestBody AdminPasswordDto update) throws AdminException {
		boolean result = service.updatepassword(update, token);	
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.ACCEPTED, "password  updated successfully", result));
		
	}
	
	
	@PostMapping("/admin/approveTheBook")
	public ResponseEntity<Response> approveTheBook(@RequestParam("bookid") Long bookId) throws BookException {
		boolean result = service.approveBook(bookId);
		if (result==true) {
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.ACCEPTED, "Book is approved", result));
		}
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.NOT_FOUND, "This book is not exist", result));

	}
	
	
	
	
}

