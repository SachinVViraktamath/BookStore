package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.CartDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.CartDetails;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.CartRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.CartService;
import com.bridgelabz.bookstore.utility.JwtService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class CartServiceImplementation implements CartService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepo;

	@Transactional
	@Override
	public Book addBooksInTOTheCart(String token, Long bookId, CartDto detail) throws UserException, BookException {
		CartDetails cart = new CartDetails();
		Long id = (Long) JwtService.parse(token);
		Users user = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));
		Book book = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND, "Book is not exist"));	
		
		
		cart.setBooksQuantity(detail.getQuantity());
		cart.setTotalPrice(detail.getPrice());	
		cart.getCartBooks().add(book);
		user.getBooksCart().add(cart);
		
		 userRepository.save(user);
		return book;
	}


@Transactional
@Override
	public List<Book> getBooksfromCart(String token) throws UserException {
	CartDetails crt=new CartDetails();
		Long id = (Long) JwtService.parse(token);
		Users user = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));		
		Book book=new Book();
		List<Book> cartBooks =crt.getCartBooks();
		return cartBooks;		
	}

	

	@Override
	public Book removeBooksFromCart(String token, Long bookId) throws UserException, BookException {
		Long id = (Long) JwtService.parse(token);
		CartDetails cart=new CartDetails();
		Users user = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));
		Book book = bookRepository.getBookById(bookId)
				.orElseThrow(() -> new  BookException(HttpStatus.NOT_FOUND, "Book is not exist"));		
				cart.getCartBooks().remove(book);
		return null;
	}

}