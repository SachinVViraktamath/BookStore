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

import com.bridgelabz.bookstore.entity.Seller;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.BookService;
import com.bridgelabz.bookstore.service.ElasticSearchService;
import com.bridgelabz.bookstore.service.SellerService;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookController {

	@Autowired
	private ElasticSearchService elasticService;

	@Autowired
	private BookService bookService;

	@GetMapping("/displayAll")
	public ResponseEntity<Response> displayAllBooks() throws BookException {
		List<Book> books = bookService.displayBooks();
		if (books != null)
			return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "books for user displayed", books));
		else
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.NOT_FOUND, "books for user not displayed", books));

	}

	@GetMapping("/displayOne")
	public ResponseEntity<Response> displayParticularBook(@RequestParam("id") Long id) throws BookException {
		Book book = bookService.displaySingleBook(id);
		if (book != null)
			return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "Particular book not displayed", book));
		else
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.NOT_FOUND, "Particular book not displayed", book));
	}

	@GetMapping("/search")
	public ResponseEntity<Response> searchBooks(@RequestParam("title") String title) {
		List<Book> books = null;
		try {
			books = elasticService.getBookByTitleAndAuthor(title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (books != null)
			return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "Books searched and Found", books));
		else
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.NOT_FOUND, "Books searched and not Found", books));

	}

	@GetMapping("/sortbyprice/Asc")
	public ResponseEntity<Response> sortByPriceLowtoHigh() throws BookException {
		List<Book> books = bookService.sortByPriceAsc();
		if (books != null)
			return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "Book by Price low to high Found", books));
		else
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.NOT_FOUND, "Book by Price low to high not Found", books));

	}

	@GetMapping("/sortbyprice/Desc")
	public ResponseEntity<Response> sortByPriceHightoLow() throws BookException {
		List<Book> books = bookService.sortByPriceDesc();
		if (books != null)
			return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "Book by Price high to low Found", books));
		else
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.NOT_FOUND, "Book by Price high to low not Found", books));

	}

	@GetMapping("/sortbynewest")
	public ResponseEntity<Response> sortByNewestArrivals() throws BookException {
		List<Book> books = bookService.sortByNewest();
		if (books != null)
			return ResponseEntity.ok().body(new Response(HttpStatus.FOUND, "Book by Newest Arrivals Found", books));
		else
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.NOT_FOUND, "Book by Newest Arrivals not Found", books));

	}

}
