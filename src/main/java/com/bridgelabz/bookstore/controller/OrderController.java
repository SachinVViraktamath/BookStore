package com.bridgelabz.bookstore.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.response.Response;


public class OrderController {

	/*

	@Autowired
	private OrderServiceImplementation service;
	
	 */
	@PostMapping("/order/orderthebook")
	public ResponseEntity<Response> orderTheBooks(String token, Long bookId,String adressType) throws UserException, BookException {
	//	Order result = service.orderTheBook( token, bookId, adressType);
		return ResponseEntity.badRequest()
				.body(new Response(HttpStatus.ACCEPTED, "Order Successfully done", 200));
	}
}
