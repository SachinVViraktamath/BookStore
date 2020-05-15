package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.Order;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.OrderService;
import com.bridgelabz.bookstore.utility.JwtService;

@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Transactional
	@Override
	public List<Order> orderTheBook(String token, Long bookId, String adressType) throws BookException, UserException {
		Long id = JwtService.parse(token);
		Random random = new Random();
		ArrayList<Book> list = new ArrayList<>();
		Users userInfo = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user does not exist"));

		Order orderDetails = new Order();
		userInfo.getBooksCart().forEach((cart) -> {
			cart.getBooksList().forEach(book -> {
				long orderId;
				list.add(book);
				orderId = random.nextInt(1000);
				if (orderId < 0) {
					orderId = orderId * -1;
				}
				orderDetails.setOrderId(orderId);
				orderDetails.setOrderPlaceTime(LocalDateTime.now());
				orderDetails.setBooksList(list);
				userInfo.getOrderBookDetails().add(orderDetails);
				if (cart.getBooksList() != null) {
					long quantity = cart.getBooksQuantity();
					for (Order orderedBooks : userInfo.getOrderBookDetails()) {
						if (orderedBooks.getOrderId().equals(orderId))
							orderedBooks.setQuantityOfBooks(quantity);
						System.out.println("sachin" + orderedBooks);
					}
					Long noOfBooks = book.getNoOfBooks() - quantity;
					book.setNoOfBooks(noOfBooks);
					bookRepository.save(book);
				}
			});

		});

		userInfo.getBooksCart().clear();
		userRepository.save(userInfo);
		return userInfo.getOrderBookDetails();
	}

}
