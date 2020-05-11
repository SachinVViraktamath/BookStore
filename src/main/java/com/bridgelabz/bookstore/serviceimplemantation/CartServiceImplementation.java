package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.UserBookCart;
import com.bridgelabz.bookstore.entity.Users;
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
	
	
	@Transactional
	@Override
	public List<UserBookCart> addBooksInTOTheCart(String token, long bookId) throws UserException, BookException {
		UserBookCart cart = new UserBookCart();
		ArrayList<Book> booklist = new ArrayList<>();
		Long id = (long) JwtService.parse(token);		
		Users user = userRepository.findbyId(id)
		.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));		
		Book book = bookRepository.getBookById(bookId)
		.orElseThrow(() -> new  BookException(HttpStatus.NOT_FOUND, "Book is not exist"));
		List<Book> books = null;
		for (UserBookCart bookcart : user.getCartBooks()) {
			books =bookcart.getBooksList();
		}			
		if (books == null) {
			long quantity = 1;
			booklist.add(book);
			cart.setDateOfbookaddedToCart(LocalDateTime.now());
			cart.setBooksList(booklist);
			cart.setBookQuantity(quantity);		
			user.getCartBooks().add(cart);
			return userRepository.save(user).getCartBooks();
		}
		
		

		return userRepository.save(user).getCartBooks();
	}

	@Transactional
	@Override
	public List<UserBookCart> getBooksfromCart(String token) throws UserException {
		Long id = (long) JwtService.parse(token);
		Users user = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));
		List<UserBookCart> cartBooks = user.getCartBooks();		
		return cartBooks;		
	}

	@Transactional
	@Override
	public List<UserBookCart> addBooksQuantityToCart(String token, long noteId, long quantity) throws UserException, BookException{
		Long id = (long) JwtService.parse(token);
		Users user = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));
		Book book = bookRepository.getBookById(id)
				.orElseThrow(() -> new  BookException(HttpStatus.NOT_FOUND, "Book is not exist"));
			for(UserBookCart cart:user.getCartBooks()) {				
				boolean isBookexist = cart.getBooksList().stream().noneMatch(books -> books.getBookId() == id);				
				if (!isBookexist) {
					if(quantity <= book.getBookQuantity())
					{
					cart.setBookQuantity(quantity);					
					return userRepository.save(user).getCartBooks();
					}
				} 
			}
			
		return null;
	}

	@Override
	public List<UserBookCart> removeBooksFromCart(String token, long bookId) throws UserException, BookException {
		Long id = (long) JwtService.parse(token);
		Users user = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));
		Book book = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new  BookException(HttpStatus.NOT_FOUND, "Book is not exist"));
			for(UserBookCart cartdetails:user.getCartBooks()) {
			boolean isBookexist = cartdetails.getBooksList().stream().noneMatch(books -> books.getBookId() == bookId);
			if (!isBookexist) {
				cartdetails.getBooksList().remove(book);
				return userRepository.save(user).getCartBooks();
			} 
		}
		return null;
	}

}
