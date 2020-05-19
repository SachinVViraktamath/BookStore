//package com.bridgelabz.bookstore.serviceimplemantation;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import com.bridgelabz.bookstore.configuration.Constants;
//import com.bridgelabz.bookstore.entity.Book;
//import com.bridgelabz.bookstore.entity.CartDetails;
//import com.bridgelabz.bookstore.entity.Order;
//import com.bridgelabz.bookstore.entity.UserAddress;
//import com.bridgelabz.bookstore.entity.Users;
//import com.bridgelabz.bookstore.exception.BookException;
//import com.bridgelabz.bookstore.exception.ExceptionMessages;
//import com.bridgelabz.bookstore.exception.UserException;
//import com.bridgelabz.bookstore.repository.BookRepository;
//import com.bridgelabz.bookstore.repository.CartRepository;
//import com.bridgelabz.bookstore.repository.OrderRepository;
//import com.bridgelabz.bookstore.repository.UserAddressRepository;
//import com.bridgelabz.bookstore.repository.UserRepository;
//import com.bridgelabz.bookstore.response.MailingandResponseOperation;
//import com.bridgelabz.bookstore.service.OrderService;
//import com.bridgelabz.bookstore.utility.JwtService;
//import com.bridgelabz.bookstore.utility.MailService;
//
//@Service
//public class OrderServiceImplementation implements OrderService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private BookRepository bookRepository;
//	@Autowired
//	private MailingandResponseOperation response;
//	@Autowired
//	CartRepository repository;
//	@Autowired
//	OrderRepository orderrepository;
//	@Autowired
//	UserAddressRepository addressRepo;
//
//	@Transactional
//	@Override
//	public List<Order> orderTheBook(String token, Long cartId, String adressType) throws BookException, UserException {
//		Long id = JwtService.parse(token);
//		Users userInfo = userRepository.findbyId(id)
//				  .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user does not exist"));		
//		
//		CartDetails cartdetails = userInfo.getBooksCart().stream().filter((cart) -> cart.getCartId() == cartId)
//				.findFirst().orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND,ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
//		
//		UserAddress address=userInfo.getAddress().stream().filter((add) -> add.getAddressType() == adressType)
//		.findFirst().orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND,"Please select address "));
//		
//		
//		Order orderDetails = new Order();
//		
//		
//		
////		boolean result=true;
////			result=	repository.findbyuserId( id);
//			CartDetails details=repository.getCart(cartId).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "cart does not exist"));
//				ArrayList<CartDetails> list= new ArrayList<>();
//				list.add(details);
//			System.out.println("#####");
//			System.out.println(details);
//		UserAddress address = addressRepo.findaddressbyType(adressType, id);
//		orderDetails.setCartBook(list);
//		System.out.println("#####");
//		orderDetails.setOrderPlaceTime(LocalDateTime.now());
//		orderDetails.setOrderStatus(" book ordered ");
//		System.out.println("*****");
//		ArrayList<Order> order=new ArrayList<Order>();
//		userInfo.getOrderBookDetails().add(orderDetails);
//		System.out.println("!!!!!");
//		System.out.println();
//		orderrepository.save(orderDetails);
//		userRepository.save(userInfo);
//		return userInfo.getOrderBookDetails();
//}
//   }
////}
//// Long id = JwtService.parse(token);
////		Random random = new Random();
////		ArrayList<Book> list = new ArrayList<>();
////		Users userInfo = userRepository.findbyId(id)
////				.orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user does not exist"));
////		Order orderDetails = new Order();
////		userInfo.getBooksCart().forEach((cart) -> {
////			cart.forEach(book -> {
////				long orderId;
////				list.add(book);
////				orderId = random.nextInt(1000);
////				if (orderId < 0) {
////					orderId = orderId * -1;
////				}
////
////				orderDetails.setOrderId(orderId);
////				orderDetails.setOrderPlaceTime(LocalDateTime.now());
////				orderDetails.setBooksList(list);
////				userInfo.getOrderBookDetails().add(orderDetails);
////				bookRepository.save(book);
////				// String mailResponse = response.fromMessage(Constants.VERIFICATION_LINK,"");
////				// MailService.sendEmail("", Constants.VERIFICATION_MSG, "");
////
////			});
////
////		});
////
////		userInfo.getBooksCart().clear();
////		userRepository.save(userInfo);
////		return userInfo.getOrderBookDetails();
////	}
