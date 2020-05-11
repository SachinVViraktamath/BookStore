package com.bridgelabz.bookstore.serviceimplemantation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.UserBookCart;
import com.bridgelabz.bookstore.entity.UserData;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.CartService;
import com.bridgelabz.bookstore.utility.JwtService;

public class CartServiceImplementation implements CartService {


	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	@Override
	public List<UserBookCart> addBooksInTOTheCart(String token, long bookId) {
		Long id = (long) JwtService.parse(token);
		long quantity = 1;
		Users user = userRepository.findbyId(id)
		.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));		
		Book book = bookRepository.getBookById(bookId)
		.orElseThrow(() -> new  BookException(HttpStatus.NOT_FOUND, "Book is not exist"));	
		List<Book> books = null;
		for (UserBookCart d : user.getCartBooks()) {
			books = d.getBooksList();
		}		
		UserBookCart cart = new UserBookCart();
		ArrayList<Book> booklist = new ArrayList<>();
		if (books == null) {
			booklist.add(book);
			cart.setPlaceTime(LocalDateTime.now());
			cart.setBooksList(booklist);
			cart.setQuantityOfBooks(quantity);
			user.getCartBooks().add(cart);
			return userRepository.save(user).getCartBooks();
		}
		/**
		 * Checking whether book is already present r not
		 */
		Optional<Book> cartbook = books.stream().filter(t -> t.getBookId() == bookId).findFirst();

		if (cartbook.isPresent()) {
			throw new UserException(401, env.getProperty("505"));
		} else {
			booklist.add(book);
			cart.setPlaceTime(LocalDateTime.now());
			cart.setBooksList(booklist);
			cart.setQuantityOfBooks(quantity);
			user.getCartBooks().add(cart);
		}

		return userRepository.save(user).getCartBooks();
	}

	@Override
	public List<UserBookCart> getBooksfromCart(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserBookCart> addBooksQuantityToCart(String token, long noteId, long quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserBookCart> removeBooksFromCart(String token, long bookId) {
		// TODO Auto-generated method stub
		return null;
	}

}
