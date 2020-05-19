package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.configuration.Constants;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.CartDetails;
import com.bridgelabz.bookstore.entity.Order;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.OrderRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.response.MailingandResponseOperation;
import com.bridgelabz.bookstore.service.OrderService;
import com.bridgelabz.bookstore.utility.JwtService;
import com.bridgelabz.bookstore.utility.MailService;

@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailingandResponseOperation response;
	@Autowired
	private OrderRepository repository;

	@Transactional
	@Override
	public List<Order> orderTheBook(String token, Long bookId, String adressType) throws BookException, UserException {
	
		Long id = JwtService.parse(token);
		Random random = new Random();
		Book bookss = null;
		long orderId;
	List<Book> books=new ArrayList<Book>();
	System.out.println("!!!!!!");
		Order orderDetails=new Order();
		Users userInfo = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user does not exist"));
	UserAddress address=userInfo.getAddress().stream().filter((add)->add.getAddressType().equals(adressType)).findFirst()
		.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "This address is not exist"));
				System.out.println("@@@@@@@");
	for (CartDetails carts : userInfo.getBooksCart()) {		
		System.out.println("######");
	 bookss=carts.getBooks();
	
	}
	 System.out.println(bookss);
	 System.out.println("$$$$$");
							orderId = random.nextInt(1000);
							
							if (orderId < 0) {
								orderId = orderId * -1;
							}							
							orderDetails.setOrderId(orderId);						
							orderDetails.setOrderPlaceTime(LocalDateTime.now());																	
							//orderDetails.getBook().add(bookss);
							
							ArrayList<Book> bookkss= new ArrayList<Book>();
								bookkss.add(bookss);
								System.out.println(bookkss);
							orderDetails.setBook(bookkss);;
							userInfo.getOrderBookDetails().add(orderDetails);							
							repository.save(orderDetails);				
						userRepository.save(userInfo);
							//String mailResponse = response.fromMessage("your order id is",	String.valueOf(orderId));
							//MailService.sendEmail(userInfo.getEmail(), Constants.VERIFICATION_MSG, mailResponse);
					
				System.out.println("^^^^^");
		return userInfo.getOrderBookDetails();

	}
}