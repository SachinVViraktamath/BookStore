package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.bookstore.entity.CartDetails;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.CartService;

import io.swagger.annotations.ApiOperation;



@RestController
public class CartController {
	
@Autowired
private CartService service;

@PostMapping("cart/addBooks")
public ResponseEntity<Response> addBookToCart(@RequestParam("bookId") Long bookId,@RequestParam("token") String token) throws UserException, BookException{
	List<CartDetails> book=service.addBooksInTOTheCart(token, bookId);
	return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "book added to cart Successfully", book));

			 
}

@ApiOperation(value = "adding quantity to cart",response = Iterable.class)
@PutMapping(value="/add_booksquantity_cart/{token}")
public ResponseEntity<Response> addBooksQuantityToCart(@PathVariable("token") String token,@RequestParam("bookId") long bookId,@RequestParam("quantity") long quantity) throws Exception {
	   List<CartDetails> cart = service.addBooksQuantityToCart(token, bookId, quantity);
	   return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "book added to cart Successfully", cart));
		 
	
}
@GetMapping("/cart/getBooks")
public ResponseEntity<Response> getBooksFromCart(@RequestParam String token) throws UserException{
	List<CartDetails> book= service.getBooksfromCart(token); 
	return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "cart books ",book));			 
}





@DeleteMapping("cart/removeBookFromCart")
public ResponseEntity<Response> removeBooksFromCart(@RequestParam("bookId") Long bookId,@RequestParam("token") String token) throws UserException, BookException{
	List<CartDetails> book=service.removeBooksFromCart(bookId,token);
	return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "book added to cart Successfully", book));
		 
}

}
