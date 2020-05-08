package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
		if (utility.verifySeller(jwt)) {
			if (bookService.sellerAddingBooks(bookdto))
				return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "seller books are added", bookdto));
			else
				return ResponseEntity.badRequest()
						.body(new Response(HttpStatus.NOT_ACCEPTABLE, "seller books are not added", bookdto));
		} else
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "Not a valid Seller"));

	}

	public ResponseEntity<Response> displayAllBooks(@RequestHeader("jwt") String jwt) {
		if (utility.verifyUser(jwt)) {
			List<Book> books = bookService.displayBooksForUser(jwt);
			if (books != null)
				return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "books for user displayed", books));
			else
				return ResponseEntity.badRequest()
						.body(new Response(HttpStatus.NOT_FOUND, "books for user not displayed", books));
		} else
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "Not a valid User"));

	}

	public ResponseEntity<Response> displayParticularBook(@RequestHeader("jwt") String jwt) {
		if (utility.verifyUser(jwt)) {
			Book book = bookService.displaySingleBookForUser(jwt);
			if (book != null)
				return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "Particular book not displayed", book));
			else
				return ResponseEntity.badRequest()
						.body(new Response(HttpStatus.NOT_FOUND, "Particular book not displayed", book));
		} else
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "Not a valid User"));

	}

	public ResponseEntity<Response> getNameOfBook(@RequestHeader("jwt") String jwt, @RequestParam("id") Long id) {
		if (utility.verifyUser(jwt)) {
			String bookName = bookService.getBookName(id);
			if (bookName != null)
				return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "Particular book not found", bookName));
			else
				return ResponseEntity.badRequest()
						.body(new Response(HttpStatus.NOT_FOUND, "books for user not found", bookName));
		} else
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_ACCEPTABLE, "Not a valid User"));
	}
}
