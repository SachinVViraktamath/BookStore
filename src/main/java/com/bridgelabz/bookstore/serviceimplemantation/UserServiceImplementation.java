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
import com.bridgelabz.bookstore.dto.UserPasswordDto;
import com.bridgelabz.bookstore.dto.UserRegisterDto;
import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.dto.UserLoginDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.UserAddressRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
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
		if (repository.FindByEmail(userInfoDto.getEmail()).isPresent()==false){
			BeanUtils.copyProperties(userInfoDto, user);
			user.setPassword(bcrypt.encode(userInfoDto.getPassword()));			
			user.setCreationTime(LocalDateTime.now());
			user.setUpdateTime(LocalDateTime.now());
			user=repository.save(user);
			String mailResponse = "http://localhost:8080/user/verify/" +JwtService.generateToken(user.getUserId(),Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(user.getEmail(),"verification", mailResponse);
		}
		return user;
	
	}

	@Transactional
	@Override
	public Users userVerification(String token) throws UserException {
		boolean value=true;
		Long id =(Long) JwtService.parse(token);
		
		Users userInfo = repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user does not exist"));
	     
		userInfo.setVerified(value);
		repository.save(userInfo);
		
			
				return userInfo;
	}
	
	@Transactional
	@Override
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

	@Transactional
	@Override
	public Users forgetPassword(String email) throws UserException {
		Users userMail = repository.FindByEmail(email).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
		// log.info("userdetails for forgetpassword" + userMail);		
			if (userMail.isVerified()==true) {
				String responsemail = response.fromMessage("http://localhost:8080/verify",
						JwtService.generateToken(userMail.getUserId(),Token.WITH_EXPIRE_TIME));
				MailService.sendEmail(userMail.getEmail(), "Verification", responsemail);
				return userMail;
			}
			else {
				return null;
			}
			
		
	}

	@Transactional
	@Override
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
	
	
	@Transactional
	@Override
	public UserAddress addAddress(UserAddressDto addressDto, String token) throws UserException {
		UserAddress users=new UserAddress();
			Long id=JwtService.parse(token);
			Users user=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
			BeanUtils.copyProperties(addressDto, users);			
			user.getAddress().add(users);
			reposit.save(users);
			
			return users;	
		
	}

	@Transactional
	@Override
	public UserAddress updateAddress(String token,UserAddressDto addDto,Long addressId) throws UserException{
		UserAddress users=new UserAddress();
		Long id=JwtService.parse(token);
		Users user=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
		UserAddress usersaddress=user.getAddress().stream().filter((address)->address.getAddressId()==addressId).findFirst().orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "Address is not exist"));
		usersaddress.setAddressType(addDto.getAddressType());	
		usersaddress.setCountry(addDto.getCountry());
		usersaddress.setDistrict(addDto.getDistrict());
		usersaddress.setPinCode(addDto.getPinCode());
		usersaddress.setState(addDto.getState());
		usersaddress.setStreet(addDto.getStreet());
		usersaddress.setTown(addDto.getTown());				
		repository.save(user);		
		return users;	
	
	}
	
	@Transactional
	@Override
	public Book addWishList(Long bookId, String token) throws UserException, BookException {
		Long id=JwtService.parse(token);
		Users user=repository.findbyId(id).			
				orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, " user not found"));
		Book books=bookRep.findById(bookId).orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND, " Book not found"));
		user.getWhishlist().add(books);	
		repository.save(user);
			return books;
	}
	
	
	@Transactional
	@Override
	public List<Book> getWishList(String token) throws UserException {
		Long id=JwtService.parse(token);
		Users user=repository.findbyId(id).			
				orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, " user not found"));		
       List<Book> books	=user.getWhishlist();
		return books;
		
	}
	
	@Transactional
	@Override
	public Book removeWishList(Long bookId, String token) throws UserException, BookException {Long id=JwtService.parse(token);
	Users user=repository.findbyId(id).			
			orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, " user not found"));
	Book books=bookRep.findById(bookId).orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND, " Book not found"));
	user.getWhishlist().remove(books);	
	
		return null;
		}
}