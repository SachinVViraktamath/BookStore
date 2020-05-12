package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.Order;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.OrderRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.OrderService;
import com.bridgelabz.bookstore.utility.JwtService;

@Service
public class OrderServiceImplementation implements OrderService {

	
	@Autowired
	private UserRepository userRepository;	
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order orderTheBook(String token, Long bookId,String adressType) throws UserException, BookException {
		Order orderDetails=new Order();
		Long id = (long) JwtService.parse(token);		
		Users user = userRepository.findbyId(id)
		.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "User is not exist"));		
		Book books = user.getUserBook().stream().filter((book) -> book.getBookId()==bookId).findFirst()
				.orElseThrow(() ->  new BookException(HttpStatus.NOT_FOUND, "User is not exist"));
		
		UserAddress address = user.getAddress().stream().filter((adress) -> adress.getAddressType()==adressType).findFirst()
				.orElseThrow(() ->  new UserException(HttpStatus.NOT_FOUND, "Invalid Adreess"));
		
		orderDetails.setBooks(books);
		orderDetails.setOrderPlaceTime(LocalDateTime.now());
		orderDetails.setUser(user);	
		orderDetails.setOrderStatus("pending");	
		orderDetails.setUserAddress(address);		
		return orderRepository.save(orderDetails);

	}


}
