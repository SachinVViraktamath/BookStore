package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.entity.CartDetails;
import com.bridgelabz.bookstore.entity.Order;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.OrderService;
import com.bridgelabz.bookstore.utility.JwtService;




@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private UserRepository userRepository;


	@Transactional
	@Override
	public List<Order> orderTheBook(String token, Long cartId, String adressType) throws BookException, UserException {

		Long id = JwtService.parse(token);
		Random random = new Random();		
		long orderId;		
		Order orderDetails = new Order();
		Users userInfo = userRepository.findbyId(id)
				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user does not exist"));
		UserAddress address = userInfo.getAddress().stream().filter((add) -> add.getAddressType().equals(adressType))
				.findFirst().orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "This address is not exist"));

		CartDetails cartdetails = userInfo.getBooksCart().stream().filter((cart) -> cart.getCartId() == cartId)
				.findFirst().orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND,
						"Cart is not exist "));
		orderId = random.nextInt(1000000);

		if (orderId < 0) {
			orderId = orderId * -1;
		}
		//orderDetails.setOrderId(orderId);
		orderDetails.setOrderPlaceTime(LocalDateTime.now());
		orderDetails.setOrderStatus("pending");
		orderDetails.setOrderTackingId(orderId);
		orderDetails.setAddress(address);
		ArrayList<CartDetails> bookkss = new ArrayList<CartDetails>();
		bookkss.add(cartdetails);
		System.out.println(bookkss);
		orderDetails.setBookDetails(bookkss);
		userInfo.getOrderBookDetails().add(orderDetails);
		userRepository.save(userInfo);	
		return userInfo.getOrderBookDetails();

	}
}