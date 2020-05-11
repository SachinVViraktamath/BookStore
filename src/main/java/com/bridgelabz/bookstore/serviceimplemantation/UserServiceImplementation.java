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
import com.bridgelabz.bookstore.dto.UserInfoDto;
import com.bridgelabz.bookstore.dto.UserLogin;
import com.bridgelabz.bookstore.entity.Seller;
import com.bridgelabz.bookstore.entity.UserData;
import com.bridgelabz.bookstore.exception.AdminNotFoundException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.response.MailingOperation;
import com.bridgelabz.bookstore.response.MailingandResponseOperation;
import com.bridgelabz.bookstore.service.UserService;
import com.bridgelabz.bookstore.utility.JwtService;
import com.bridgelabz.bookstore.utility.MailService;

import com.bridgelabz.bookstore.utility.JwtService.Token;

@Service
public class UserServiceImplementation implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);
	private UserData user = new UserData();

	@Autowired
	private MailingandResponseOperation response;

	@Autowired
	private MailingOperation mailObject;

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@SuppressWarnings("null")
	@Transactional
	@Override
	public UserData userRegistration(UserInfoDto userInfoDto) throws UserNotFoundException {
		
		Date date = new Date();
		UserData checkmail = repository.FindByEmail(userInfoDto.getEmail());
		System.out.println("@@@");
		if (checkmail == null) {
			BeanUtils.copyProperties(userInfoDto, UserData.class);

			String pwd = bcrypt.encode(userInfoDto.getPassword());
			checkmail.setPassword(pwd);
			checkmail.setCreationTime(date);
			checkmail.setUpdateTime(date);
			checkmail.setVerified(false);
			repository.save(checkmail);

			String responsemail = "http://localhost:8080/verify/"
					+ JwtService.generateToken(checkmail.getUserId(), Token.WITH_EXPIRE_TIME);
			System.out.println(response);
			mailObject.setEmail(user.getEmail());
			mailObject.setMessage(responsemail);
			mailObject.setSubject("verification");
			MailService.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());

		}
		else {
			throw new UserNotFoundException(HttpStatus.NOT_ACCEPTABLE,"Admin user already exist");

		}
		return checkmail;
	}

	@Transactional
	@Override
	public boolean userVerification(String token) throws UserNotFoundException {
		Long id = (long) JwtService.parse(token);
		repository.findbyId(id);
		return true;
	}

	public UserData userLogin(UserLogin login) throws UserNotFoundException {

		UserData getMail = repository.FindByEmail(login.getEmail());

		if (getMail.isVerified()) {
			try {
				if (getMail.getEmail().equals(login.getEmail())) {

					boolean passwordCheck = bcrypt.matches(login.getPassword(), getMail.getPassword());

					if (passwordCheck) {
						mailObject.setEmail(getMail.getEmail());
						mailObject.setSubject("sendig by fundoo app admin");
						mailObject.setMessage("susccessfully login to fundoo app");
						MailService.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());
						return getMail;
					} else {
						mailObject.setEmail(getMail.getEmail());
						mailObject.setSubject("sendig by fundoo app admin");
						mailObject.setMessage("susccessfully login to fundoo app");
						MailService.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());
					}

				}
				return null;

			}

			catch (Exception e) {
				throw new UserNotFoundException(HttpStatus.BAD_REQUEST, "Login unsuccessfull");
			}

		}
		return null;
	}

	public UserData forgetPassword(String email) throws UserNotFoundException {
		UserData userMail = repository.FindByEmail(email);
		// log.info("userdetails for forgetpassword" + userMail);
		if (userMail != null) {
			if (userMail.isVerified()) {
				mailObject.setEmail(userMail.getEmail());
				mailObject.setSubject("sending by admin");
				mailObject.setMessage("http://localhost:8082/updatePassword/" + JwtService.parse(email));
				return user;
			}
		} else {
			throw new UserNotFoundException(HttpStatus.BAD_REQUEST, "Login unsuccessfull");
		}
		return null;
	}

	public UserData updatePassword(UpdateUserPassword forgetpass, String token)
			throws JWTVerificationException, Exception {
		if (forgetpass.getNewPassword().equals(forgetpass.getConfirmPassword())) {
			logger.info("id in verification", JwtService.parse(token));
			Long id = JwtService.parse(token);
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
