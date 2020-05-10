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
import com.bridgelabz.bookstore.dto.AdminLogin;
import com.bridgelabz.bookstore.dto.UpdateAdminPassword;
import com.bridgelabz.bookstore.entity.AdminEntity;
import com.bridgelabz.bookstore.exception.AdminNotFoundException;
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
	public ResponseEntity<Response> registration(@RequestBody AdminDto admiInformation) throws AdminNotFoundException {

		AdminEntity result = service.adminRegistartion(admiInformation);

		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.NOT_ACCEPTABLE, "Registration Successfull", result));
		

	}
	
	
	
	@GetMapping("/admin/verify/{token}")
	public ResponseEntity<Response> verficationAdmin(@PathVariable("token") String token) throws AdminNotFoundException {
		boolean update = service.verifyAdmin(token);
		if (update) {
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.ACCEPTED, "Registration Successfull", update));		

		}return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.NOT_ACCEPTABLE, "Registration Successfull", update));
		
	}
	
	
	@PostMapping("/admin/login")
	public ResponseEntity<Response> loginAdmin(@RequestBody AdminLogin adminLogin) throws AdminNotFoundException {
		AdminEntity userInformation = service.loginToAdmin(adminLogin);
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.NOT_ACCEPTABLE, "Login Successfull", userInformation));		
	}
	
	
	
	@PostMapping("/admin/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) throws AdminNotFoundException {
		AdminEntity result = service.isAdminExist(email);
		if (result!=null) {
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.ACCEPTED, "Admin exited", result));
		}
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.ACCEPTED, "admin Does not exit in the given email id", result));

	}

	/**
	 * API used for the update
	 * @throws UsersException 
	 * 
	 */
	@PutMapping("/admin/update/{token}")
	public ResponseEntity<Response> updatePassword(@PathVariable("token") String token,
			@RequestBody UpdateAdminPassword update) throws AdminNotFoundException {
		boolean result = service.updatepassword(update, token);	
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.ACCEPTED, "password  updated successfully", result));
		
	}
	
	
	
	
	
}

