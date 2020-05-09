package com.bridgelabz.bookstore.serviceimplemantation;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.bookstore.dto.UpdateUserPassword;
import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.dto.UserLogin;
import com.bridgelabz.bookstore.entity.UserData;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.response.MailingOperation;
import com.bridgelabz.bookstore.response.MailingandResponseOperation;
import com.bridgelabz.bookstore.service.UserService;
import com.bridgelabz.bookstore.utility.JwtService;
import com.bridgelabz.bookstore.utility.MailService;


import com.bridgelabz.bookstore.utility.JwtService.Token;

@Service
public class UserServiceImplementation implements UserService{
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);
	private UserData user=new UserData();

	@Autowired
	private MailingandResponseOperation response;
	
	@Autowired
	private MailingOperation mailObject;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private JwtService jwt;
	
	
	@Transactional
	@Override
	public UserData userRegistration(UserDto userdto)  throws UserNotFoundException{
		Date date=new Date();
		UserData checkmail= repository.FindByEmail(userdto.getEmail());
		
		if(checkmail==null) {
			user.setFirstName(userdto.getUserName());
			user.setLastName(userdto.getUserLastName());
			user.setPassword(bcrypt.encode(userdto.getPassword()));
			user.setEmail(userdto.getEmail());
			user.setGender(userdto.getGender());
			user.setPhNo(userdto.getPhoneNumber());
			user.setCreationTime(date);
			user.setUpdateTime(date);
			user.setVerified(false);	
			repository.save(user);
			
			UserData isUserAvailableTwo = repository.FindByEmail(userdto.getEmail());
			
			System.out.println(isUserAvailableTwo.getUserId());
			
			String email=user.getEmail();
			
			String responsemail = "http://localhost:8080/verify/" + jwt.generateToken(isUserAvailableTwo.getUserId(),Token.WITH_EXPIRE_TIME);
			System.out.println(response);
			mailObject.setEmail(user.getEmail());
			mailObject.setMessage(responsemail);
			mailObject.setSubject("verification");
			MailService.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());			
			return user;
			
		}
		return null;	
	}
	
	@Transactional
	@Override
	public boolean userVerification(String token) throws UserNotFoundException {
		Long id = (long) jwt.parse(token);
		repository.findbyId(id);
		return true;
	}
	
	public UserData userLogin(UserLogin login) throws UserNotFoundException{
		
		UserData getMail = repository.FindByEmail(login.getEmail());
		
		if(getMail.isVerified()) {
			try {
				if (getMail.getEmail().equals(login.getEmail())) {
					
					boolean passwordCheck = bcrypt.matches(login.getPassword(), getMail.getPassword());
					
					if(passwordCheck) {
						mailObject.setEmail(getMail.getEmail());
						mailObject.setSubject("sendig by fundoo app admin");
						mailObject.setMessage("susccessfully login to fundoo app");
						MailService.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());			
						return getMail;
					}
					else {
						mailObject.setEmail(getMail.getEmail());
						mailObject.setSubject("sendig by fundoo app admin");
						mailObject.setMessage("susccessfully login to fundoo app");
						MailService.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());
					}

			}
					return null;
			
		}
		
			catch(Exception e) {
					throw new UserNotFoundException(HttpStatus.BAD_REQUEST, "Login unsuccessfull");
			}
		
		}
		return null;
	}
	

	public UserData forgetPassword(String email) throws UserNotFoundException{
		UserData userMail = repository.FindByEmail(email);
		//log.info("userdetails for forgetpassword" + userMail);
		if (userMail != null) {
			if (userMail.isVerified()) {
				mailObject.setEmail(userMail.getEmail());
				mailObject.setSubject("sending by admin");
				mailObject.setMessage("http://localhost:8082/updatePassword/" + jwt.parse(email));
				return user;
			}
		} else {
				throw new UserNotFoundException(HttpStatus.BAD_REQUEST, "Login unsuccessfull");
		}
		return null;
	}
	
	
	
	public UserData updatePassword(UpdateUserPassword forgetpass, String token) throws JWTVerificationException, Exception {
		if (forgetpass.getNewPassword().equals(forgetpass.getConfirmPassword())) {
			logger.info("id in verification", jwt.parse(token));
			Long id = jwt.parse(token);
			UserData isIdVerified = repository.findbyId(id);
			if (isIdVerified.isVerified()) {
				isIdVerified.setPassword(forgetpass.getConfirmPassword());
				repository.changepassword(isIdVerified.getPassword(), id);
				return isIdVerified;
			}
		}
		return null;

	}

}
