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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.SellerLoginDto;
import com.bridgelabz.bookstore.dto.SellerPasswordUpdateDto;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.Seller;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.SellerService;
import com.bridgelabz.bookstore.utility.JwtService;

@RestController
public class SellerController {
	
@Autowired
 private SellerService service;


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
@GetMapping("seller/allSellers")
public ResponseEntity<Response> getAllUsers() {
	List<Seller> sellers = service.getSellers();
	return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "listed all the sellers", sellers));
}
@PutMapping("seller/updateSellerPassword")
public ResponseEntity<Response> updatePassword(@RequestBody SellerPasswordUpdateDto update,@PathVariable("token") String token) throws Exception {
	Boolean passwordUpdate=service.updatePassword(update, token);
	if(passwordUpdate) {
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "Password update", update));
	}
	return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "updation failed", token));
}

@PostMapping("/book/addBookBySeller/")
public ResponseEntity<Response> addBookBySeller(@RequestBody BookDto dto, @RequestPart MultipartFile file,@PathVariable("token") String token) {
	System.out.println("@#@@@");
	boolean addBook = service.addBookBySeller(token, dto,file);
	if (addBook) {
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, "verification mail has send successfully", token));
	}
	return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "verification failed", token));
}


@PutMapping("/verifyBooks/admin")
public ResponseEntity<Response> verifyBookByAdmin(@PathVariable Long id, @RequestHeader("token") String token) {
	boolean verify = service.bookVerify(token, id);
	if (verify) {

		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "book approved", token));
	}
	return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, " book rejected", token));
}
}

