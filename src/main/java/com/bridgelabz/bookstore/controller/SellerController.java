package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.SellerLoginDto;
import com.bridgelabz.bookstore.dto.SellerPasswordUpdateDto;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.Admin;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.Seller;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.SellerException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.SellerService;
import com.bridgelabz.bookstore.utility.JwtService;

@RestController
public class SellerController {
	
@Autowired
 private SellerService service;

/* API for seller registration */
@PostMapping("seller/Registration")
public ResponseEntity<Response> sellerRegistration(@RequestBody SellerDto dto){
	
	Seller reg = service.register(dto);
	if (reg!=null) {
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "Seller Registered Successfully", dto));
		
	}
	return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "seller is already exist",dto));
				 
}
/* API for seller login */
@PostMapping("seller/Login")
public ResponseEntity<Response> Login(@RequestBody SellerLoginDto login) {
	Seller seller = service.login(login);
	if (seller != null) {
		String token = JwtService.generateToken(seller.getSellerId(), null);
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "Login Successfully", login));
	}
	return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "Login failed",login));

}

/* API for verifying the token generated for the email */
@PostMapping("/verify/{token}")
public ResponseEntity<Response> verify(@PathVariable("token") String token) throws Exception {
	boolean verification = service.verify(token);
	if (verification) {
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "verified", token));
	}
	return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "verification failed",token));
}
/* API for  get all the verified sellers  */
@GetMapping("seller/allSellers")
public ResponseEntity<Response> getAllUsers() {
	List<Seller> sellers = service.getSellers();
	return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "listed all the sellers", sellers));
}
/* API for seller forgetPassword */

@PostMapping("/seller/forgotpassword")
public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) throws SellerException {
	Seller seller = service.forgetPassword(email);
	if (seller!=null) {
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.ACCEPTED, "seller existed", seller));
	}
	return ResponseEntity.badRequest()
			.body(new Response(HttpStatus.NOT_FOUND, "admin does not exit in the given email id", seller));

}
/* API for seller updating password */
@PutMapping("seller/updateSellerPassword")
public ResponseEntity<Response> updatePassword(@RequestBody SellerPasswordUpdateDto update,@RequestParam("token") String token) throws SellerException {
	Boolean passwordUpdate=service.updatePassword(update, token);
	if(passwordUpdate) {
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "Password update", update));
	}
	return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "updation failed", token));
}
/* API for seller adding books for approval */
@PostMapping("/seller/addBookBySeller/")
public ResponseEntity<Response> addBookBySeller(@RequestBody BookDto dto,@RequestHeader("token") String token) {
	
	Book addBook = service.addBookBySeller(token, dto);
	if (addBook!=null) {
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, "verification mail has send successfully", token));
	}
	return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "verification failed", token));
}


}

