package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.BookService;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping("/add")
	public ResponseEntity<Response> addNewBooks(@RequestHeader("jwt") String jwt, @RequestBody BookDto bookdto) {
		if (true) {
			if (bookService.sellerAddingBooks(bookdto))
				return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "seller books are added", bookdto));
			else
				return ResponseEntity.badRequest()
						.body(new Response(HttpStatus.NOT_ACCEPTABLE, "seller books are not added", bookdto));
		} else
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "Not a valid Seller"));

	}

	@GetMapping("/displayAll")
	public ResponseEntity<Response> displayAllBooks(@RequestHeader("jwt") String jwt) {
		if (true) {
			List<Book> books = bookService.displayBooksForUser(jwt);
			if (books != null)
				return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "books for user displayed", books));
			else
				return ResponseEntity.badRequest()
						.body(new Response(HttpStatus.NOT_FOUND, "books for user not displayed", books));
		} else
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "Not a valid User"));

	}

	@GetMapping("/displayOne")
	public ResponseEntity<Response> displayParticularBook(@RequestHeader("jwt") String jwt) {
		if (true) {
			Book book = bookService.displaySingleBookForUser(jwt);
			if (book != null)
				return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "Particular book not displayed", book));
			else
				return ResponseEntity.badRequest()
						.body(new Response(HttpStatus.NOT_FOUND, "Particular book not displayed", book));
		} else
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "Not a valid User"));

	}

	public ResponseEntity<Response> searchBooksForUser(@RequestHeader("jwt") String jwt) {
		if (true) {
			List<Book> books = bookService.searchAllBooks(jwt);
			if (books != null)
				return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "Book searched and Found", books));
			else
				return ResponseEntity.badRequest()
						.body(new Response(HttpStatus.NOT_FOUND, "Book searched and not Found", books));
		} else
			return ResponseEntity.badRequest().body(new Response(HttpStatus.UNAUTHORIZED, "Not a valid User"));

	}

}
