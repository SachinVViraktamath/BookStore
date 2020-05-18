package com.bridgelabz.bookstore.serviceimplemantation;


import java.util.List;
import javax.transaction.Transactional;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.CartDetails;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.ExceptionMessages;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.CartService;
import com.bridgelabz.bookstore.utility.JwtService;

@Service
public class CartServiceImplementation implements CartService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	BookRepository bookRepository;

	@Transactional
	@Override
	public List<CartDetails> addBooksInTOTheCart(String token, Long bookId) throws UserException, BookException {
		Long id = JwtService.parse(token);
		long quantity = 1;
		int flag = 0;
		CartDetails cart = new CartDetails();
		Users userInfo = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user not found"));
		ArrayList<Book> booklist = new ArrayList<>();
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND, "book not found"));
		if ((userInfo.getBooksCart()).isEmpty()) {
			booklist.add(book);
			cart.setBooksList(booklist);
			cart.setQuantityOfBooks(quantity);
			userInfo.getBooksCart().add(cart);
			userRepository.save(userInfo);
			Book bookkk=bookRepository.findById(bookId).orElseThrow(
					() -> new BookException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
			Long quant=bookkk.getNoOfBooks()-1;
			bookkk.setNoOfBooks(quant);
			bookRepository.save(bookkk);
		} else {
			for (CartDetails cartss : userInfo.getBooksCart()) {
				for (Book books : cartss.getBooksList()) {
					if (books.getBookId().equals(bookId)) {
						throw new BookException(HttpStatus.NOT_FOUND, "Book is already prasent in cart");
					} else {
						flag = 1;
					}
				}
			}

		}

		if (flag == 1) {
			booklist.add(book);
			cart.setBooksList(booklist);
			cart.setQuantityOfBooks(quantity);
			userInfo.getBooksCart().add(cart);
			userRepository.save(userInfo);
			Book bookkk=bookRepository.findById(bookId).orElseThrow(
					() -> new BookException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
			Long quant=bookkk.getNoOfBooks()-1;
			bookkk.setNoOfBooks(quant);
			bookRepository.save(bookkk);
			
		}

		return null;
	}

	@Transactional
	@Override
	public List<CartDetails> getBooksfromCart(String token) throws UserException {
		Long id = JwtService.parse(token);
		Users userInfo = userRepository.findbyId(id).orElseThrow(
				() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		return userInfo.getBooksCart();
	}

	@Transactional
	@Override
	public List<CartDetails> removeBooksFromCart(Long bookId, String token) throws UserException, BookException {
		Long id = JwtService.parse(token);
		long setZero = 0;
		
		Users userInfo = userRepository.findbyId(id).orElseThrow(
				() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		Book book = bookRepository.findById(bookId).orElseThrow(
				() -> new BookException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));

		for (CartDetails cart : userInfo.getBooksCart()) {
			for (Book books : cart.getBooksList()) {
				if (book.getBookId() == bookId) {
					cart.getBooksList().remove(books);
					cart.setQuantityOfBooks(setZero);	
				    long removeQuant=cart.getQuantityOfBooks();
					userRepository.save(userInfo);
					Book bookkk=bookRepository.findById(books.getBookId()).orElseThrow(
							() -> new BookException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
					Long quant=(bookkk.getNoOfBooks()+removeQuant);
					bookkk.setNoOfBooks(quant);
					bookRepository.save(bookkk);
					return null;
				}

			}
		}

		return null;
	}

	@Transactional
	@Override
	public List<CartDetails> addBooksQuantityToCart(String token, Long cartId) throws UserException, BookException {
		long quantity = 1;

		Long id = JwtService.parse(token);
		Users userInfo = userRepository.findbyId(id).orElseThrow(
				() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		CartDetails cartdetails = userInfo.getBooksCart().stream().filter((cart) -> cart.getCartId() == cartId)
				.findFirst().orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND,
						ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		if (cartdetails.getQuantityOfBooks() != null) {

			long qunt = cartdetails.getQuantityOfBooks();
			long total = (qunt + quantity);
			cartdetails.setQuantityOfBooks(total);
			userInfo.getBooksCart().add(cartdetails);
			for (Book bookk : cartdetails.getBooksList()) {
				Book bbb = bookRepository.findById(bookk.getBookId())
						.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND,
								ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
			//	bbb.setNoOfBooks(bbb.getNoOfBooks() + 1);
				bookRepository.save(bbb);
				Book bookkk=bookRepository.findById(bookk.getBookId()).orElseThrow(
						() -> new BookException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
				Long quant=bookkk.getNoOfBooks();
				System.out.print("ssssss"+quant);
				Long quantities=quant-1;
				bookkk.setNoOfBooks(quantities);
				bookRepository.save(bookkk);
			}
			// bookRepository.findById(bookk .getBookId());

		}

		userRepository.save(userInfo);
		return null;
	}

	@Transactional
	@Override
	public List<CartDetails> decreasingBooksQuantityInCart(String token, Long cartId)
			throws UserException, BookException {
		long quantity = 1;
		Long id = JwtService.parse(token);
		Users userInfo = userRepository.findbyId(id).orElseThrow(
				() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		CartDetails cartdetails = userInfo.getBooksCart().stream().filter((cart) -> cart.getCartId() == cartId)
				.findFirst().orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND,
						ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		if (cartdetails.getQuantityOfBooks() != null) {

			long qunt = cartdetails.getQuantityOfBooks();
			long total = (qunt - quantity);
			cartdetails.setQuantityOfBooks(total);
			/*
			 * if(cartdetails.getQuantityOfBooks()==null) {
			 * cartdetails.getBooksList().remove(books); }
			 */
			userInfo.getBooksCart().add(cartdetails);
			for (Book bookk : cartdetails.getBooksList()) {
//				Book bbb = bookRepository.findById(bookk.getBookId())
//						.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND,
//								ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
				//bookk.setNoOfBooks(bookk.getNoOfBooks() - 1);
				//bookRepository.save(bookk);
				Book bookkk=bookRepository.findById(bookk.getBookId()).orElseThrow(
						() -> new BookException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
				Long quant=bookkk.getNoOfBooks();
				Long quantities=quant+1;
				bookkk.setNoOfBooks(quantities);
				bookkk.setNoOfBooks(quant);
				bookRepository.save(bookkk);
			}
		}

		userRepository.save(userInfo);
		return null;
	}

}
