package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.SellerEntity;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.SellerService;
import com.bridgelabz.bookstore.utility.JwtService;

@RestController
public class SellerController {
	
@Autowired
 private SellerService service;
@Autowired
private JwtService generator;

@PostMapping("seller/Registration")
public ResponseEntity<Response> sellerRegistration(@RequestBody SellerDto dto){
	System.out.println("####");
	boolean reg = service.register(dto);
	if (reg) {
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "Seller Registered Successfully", dto));
		
	}
	return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "seller is already exist",dto));
				 
}
/* API for seller login */
@PostMapping("seller/Login")
public ResponseEntity<Response> Login(@RequestBody LoginDto login) {
	SellerEntity seller = service.login(login);
	if (seller != null) {
		String token = generator.generateToken(seller.getSellerId(), null);
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
	List<SellerEntity> sellers = service.getSellers();
	return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "listed all the sellers", sellers));
}
}

