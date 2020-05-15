package com.bridgelabz.bookstore.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.entity.Order;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.serviceimplemantation.OrderServiceImplementation;

@RestController
public class OrderController {

	

	@Autowired
	private OrderServiceImplementation service;
	
	 
	@PostMapping("/order/orderthebook")
	public ResponseEntity<Response> orderTheBooks(String token, Long bookId,String adressType) throws UserException, BookException {
		List<Order> result = service.orderTheBook( token, bookId, adressType);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, "Order Successfully done", result));
	}
}
