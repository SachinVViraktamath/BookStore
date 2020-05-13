package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.dto.CartDto;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.CartService;



@RestController
public class CartController {
	
@Autowired
private CartService service;

@PostMapping("cart/addBooks")
public ResponseEntity<Response> addBookToCart(@RequestBody CartDto cartdto,@RequestParam("bookId") Long bookId,@RequestParam("token") String token) throws UserException, BookException{
	Book book=service.addBooksInTOTheCart(token, bookId,cartdto);
if(book!=null) {
	return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "book added to cart Successfully", book));
	
}
return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "book is already exist in cart",book));
			 
}

@GetMapping("/cart/getBooks")
public ResponseEntity<Response> getBooksFromCart(@PathVariable("token") String token) throws UserException{
	List<Book> book= service.getBooksfromCart(token);
if(book!=null) {
	return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "cart books ",book));
	
}
return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "book is not exist in cart",book));
			 
}
@DeleteMapping("cart/removeBookFromCart")
public ResponseEntity<Response> removeBooksFromCart(@RequestParam("bookId") Long bookId,@RequestParam("token") String token) throws UserException, BookException{
	Book book=service.removeBooksFromCart(token, bookId);
if(book!=null) {
	return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "book added to cart Successfully", book));
	
}
return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "book is already exist in cart",book));
			 
}

}
