package com.bridgelabz.bookstore.serviceimplemantation;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.CartDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.BookQuantity;
import com.bridgelabz.bookstore.entity.CartDetails;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.CartService;
import com.bridgelabz.bookstore.utility.JwtService;


@Service
public class CartServiceImplementation implements CartService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public List<CartDetails> getBooksfromCart(String token) throws UserException {
		Long id = JwtService.parse(token);
		Users user = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));
		List<CartDetails> cartBooks = user.getBooksCart();
		return cartBooks;
	}

	public Users cartbooks(Book book, Users user) {
		long quantity = 1;
		CartDetails cart = new CartDetails();
		BookQuantity qunatityofbook = new BookQuantity();
		ArrayList<Book> booklist = new ArrayList<>();
		ArrayList<BookQuantity> quantitydetails = new ArrayList<BookQuantity>();
		booklist.add(book);
		cart.setBooksList(booklist);
		qunatityofbook.setBookQty(quantity);
		quantitydetails.add(qunatityofbook);
		cart.setQuantityOfBooks(quantitydetails);
		user.getBooksCart().add(cart);
		return user;
	}

	@Transactional
	@Override
	public List<CartDetails> addBooksInTOTheCart(String token, Long bookId) throws UserException, BookException {
		Long id = JwtService.parse(token);

		Users user = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND, "User is not exist"));
		List<Book> books = null;
		for (CartDetails d : user.getBooksCart()) {
			books = d.getBooksList();
		}
		if (books == null) {
			Users userdetails = this.cartbooks(book, user);
			return userRepository.save(userdetails).getBooksCart();
		}
		Optional<Book> cartbook = books.stream().filter(t -> t.getBookId() == bookId).findFirst();
		if (cartbook.isPresent()) {
			throw new UserException(HttpStatus.NOT_FOUND, "User is not exist");
		} else {
			Users userdetails = this.cartbooks(book, user);
			return userRepository.save(userdetails).getBooksCart();

		}
	}

	@Transactional
	@Override
	public List<CartDetails> removeBooksFromCart(Long bookId, String token) throws UserException, BookException {
		Long id = JwtService.parse(token);

		Users user = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND, "User is not exist"));
		for (CartDetails cart : user.getBooksCart()) {
			boolean notExist = cart.getBooksList().stream().noneMatch(books -> books.getBookId() == bookId);
			if (!notExist) {
				cart.getBooksList().remove(book);
				return userRepository.save(user).getBooksCart();
			}
		}
		return null;
	}

	@Transactional
	@Override
	public List<CartDetails> addBooksQuantityToCart(String token, Long bookId, CartDto dto)
			throws UserException, BookException {

		Long id = JwtService.parse(token);
		Long quantityId = dto.getCartId();
		Long quantity = dto.getQuantity();
		Users user = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));
		boolean notExist = false;
		for (CartDetails carts : user.getBooksCart()) {
			if (!carts.getBooksList().isEmpty()) {

				notExist = carts.getBooksList().stream()
						.noneMatch(books -> books.getBookId().equals(bookId) && books.getNoOfBooks() > quantity);

				ArrayList<BookQuantity> qt = new ArrayList<BookQuantity>();
				if (!notExist) {

					for (BookQuantity qant : carts.getQuantityOfBooks()) {
						if (qant.getQuantityId().equals(quantityId)) {

							BookQuantity qunatityofbook = new BookQuantity(quantity + 1);

							qt.add(qunatityofbook);
							carts.setQuantityOfBooks(qt);
							return userRepository.save(user).getBooksCart();

						}
					}

				}
			}
		}
		if (notExist == false) {
			throw new UserException(HttpStatus.NOT_FOUND, "User is not exist");
		}
		return null;

	}

	@Override
	public List<CartDetails> decreasingBooksQuantityInCart(String token, Long bookId, CartDto cartid)
			throws UserException {

		Long id = JwtService.parse(token);
		Long quantityId =  cartid.getCartId();
		Long quantity =cartid.getQuantity();
		Users user = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));
		
		
		boolean notExist = false;
		for (CartDetails carts : user.getBooksCart()) {
			
			if (!carts.getBooksList().isEmpty()) {
				notExist = carts.getBooksList().stream().noneMatch(books -> books.getBookId()==bookId);
				
				ArrayList<BookQuantity> qt = new ArrayList<BookQuantity>();
				if (!notExist) {
					
					for (BookQuantity qant : carts.getQuantityOfBooks()) {
						
						if (qant.getQuantityId().equals(quantityId)) {
							BookQuantity qunatityofbook = new BookQuantity(quantity - 1);
							qt.add(qunatityofbook);
							
							carts.setQuantityOfBooks(qt);
							return userRepository.save(user).getBooksCart();

						}
					}

				}
			}
		}
		if (notExist == false) {
			throw new UserException(HttpStatus.NOT_FOUND, "User is not exist");
		}
		return null;

	}

}