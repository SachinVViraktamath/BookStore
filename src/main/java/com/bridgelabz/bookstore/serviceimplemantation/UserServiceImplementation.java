package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.auth0.jwt.exceptions.JWTVerificationException;

import com.bridgelabz.bookstore.entity.Seller;

import com.bridgelabz.bookstore.exception.AdminNotFoundException;



import com.bridgelabz.bookstore.dto.UserPasswordDto;
import com.bridgelabz.bookstore.dto.UserRegisterDto;
import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.dto.UserLoginDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.UserException;
<<<<<<< HEAD
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.UserAddressRepository;
=======

>>>>>>> 04dd952da95c9674787fa84e69beb992414dfbb4
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.response.MailingOperation;
import com.bridgelabz.bookstore.response.MailingandResponseOperation;
import com.bridgelabz.bookstore.service.UserService;
import com.bridgelabz.bookstore.utility.JwtService;
import com.bridgelabz.bookstore.utility.MailService;

import com.bridgelabz.bookstore.utility.JwtService.Token;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private MailingandResponseOperation response;

	@Autowired
	private MailingOperation mailObject;

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private UserAddressRepository reposit;
	
	@Autowired
	private BookRepository bookRep;

	@Transactional
	@Override
	public Users userRegistration(@Valid UserRegisterDto userInfoDto) throws UserException {
		Users user = new Users();		
		Users isEmail =repository.FindByEmail(userInfoDto.getEmail()).
				orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));		
			BeanUtils.copyProperties(userInfoDto, isEmail);
			user.setPassword(bcrypt.encode(userInfoDto.getPassword()));			
			user.setCreationTime(LocalDateTime.now());
			user.setUpdateTime(LocalDateTime.now());
			user=repository.save(user);
			String mailResponse = "http://localhost:8080/user/verify/" +JwtService.generateToken(user.getUserId(),Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(user.getEmail(),"verification", mailResponse);
			return user;
	
	}

	@Transactional
	@Override
	public Users userVerification(String token) throws UserException {
		long id = JwtService.parse(token);
		Users userInfo = repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
	      if (!userInfo.isVerified()) {
				userInfo.setVerified(true);
				repository.findbyId(userInfo.getUserId());
				return userInfo;
			} else {
				throw new UserException(HttpStatus.NOT_ACCEPTABLE," User already exist");
			}
		
	}

	public Users userLogin( UserLoginDto login) throws UserException {
		Users user = repository.FindByEmail(login.getEmail()).
				orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
		if ((user.isVerified() == true) && (bcrypt.matches(login.getPassword(), user.getPassword()))) {
			return user;
		} else {
			String mailResponse = response.fromMessage("http://localhost:8080/verify",
					JwtService.generateToken(user.getUserId(),Token.WITH_EXPIRE_TIME));
			MailService.sendEmail(login.getEmail(), "Verification", mailResponse);
			throw new UserException(HttpStatus.ACCEPTED, "Login unsuccessfull");
		}
	}

	public Users forgetPassword(String email) throws UserException {
		Users userMail = repository.FindByEmail(email).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
		// log.info("userdetails for forgetpassword" + userMail);		
			if (userMail.isVerified()) {
				mailObject.setEmail(userMail.getEmail());
				mailObject.setSubject("sending by admin");
				mailObject.setMessage("http://localhost:8082/updatePassword/" + JwtService.parse(email));
				return userMail;
			}
			return userMail;
		
	}

	public boolean updatePassword(UserPasswordDto forgetpass, String token) throws UserException {
		Long id = null;	
		boolean passwordupdateflag=false;
			
		id = (Long) JwtService.parse(token);
		Users userinfo=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
		
		if(bcrypt.matches(forgetpass.getOldPassword(),userinfo.getPassword())) {
		String epassword = bcrypt.encode(forgetpass.getCnfPassword());
		forgetpass.setCnfPassword(epassword);
		 repository.updatePassword(forgetpass, id);
			return passwordupdateflag;
		}else {
			
			throw new UserException(HttpStatus.BAD_REQUEST, "password update failed");
		}

	}
	
	
	@Override
	public UserAddress addAddress(UserAddressDto addressDto, String token) throws UserException {
		
			long id=JwtService.parse(token);
			Users user=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
			UserAddress	add=new UserAddress();
			BeanUtils.copyProperties(addressDto, user);
			reposit.save(add);
			return add;	
		
	}

	@Override
	public UserAddress updateAddress(String token,UserAddressDto addDto,long addressId) throws UserException{
		    long id =  JwtService.parse(token);
			Users user=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
			UserAddress add =reposit.findbyId(addressId).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
			reposit.updateAdd(add.getStreet(),add.getTown(),add.getDistrict(),add.getState(),add.getCountry(),add.getPinCode());
						
			return add;	
	
	}
	
	@Transactional
	@Override
	public Book addWishList(Long bookId, String token, String email) throws UserException {
		Users wish=repository.FindByEmail(email).
				orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, " Not Added to WishList"));
		
		Long id = (Long) JwtService.parse(token);
		
			if(wish!=null) {
				Optional<Book> book=bookRep.getBookById(id);
				wish.getUserBook().add(book);	
				return book;
		}
			return null;
	}
	
	
	
	@Transactional
	@Override
	public List<Book> getWish(String token) throws UserException {
		Users user=new Users();
		Long id = (Long) JwtService.parse(token);
		user = repository.findById(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, " Not selected any book to wishlist"));
		List<Book> note = user.getUserBook();
		return note;
	}
	
	@Transactional
	@Override
	public Book removeWishList(Long bookId, String token, String email) throws UserException {
		Users user=new Users();
		Users wish=repository.getUser(email).
				orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, " WishList is empty"));
		
		Long id = (Long) JwtService.parse(token);
		
			if(wish!=null) {
				Book book=bookRep.findbyId(id);
				wish.getUserBook().remove(book);	
				return book;
		}
			return null;
	}
}
