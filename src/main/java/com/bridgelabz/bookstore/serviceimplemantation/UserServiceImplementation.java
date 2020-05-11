package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.bookstore.dto.UpdateUserPassword;
import com.bridgelabz.bookstore.dto.UserInfoDto;
import com.bridgelabz.bookstore.dto.UserLogin;
import com.bridgelabz.bookstore.entity.Seller;
import com.bridgelabz.bookstore.entity.UserData;
import com.bridgelabz.bookstore.exception.AdminNotFoundException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
=======
import com.bridgelabz.bookstore.dto.UserPasswordDto;
import com.bridgelabz.bookstore.dto.UserRegisterDto;
import com.bridgelabz.bookstore.dto.UserLoginDto;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.UserException;
>>>>>>> 9065aede7662627b3e22ed74675e30d17d3b9b66
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

	@Transactional
	@Override
	public Users userRegistration(@Valid UserRegisterDto userInfoDto) throws UserException {
		Users user = new Users();
		
		Users isEmail =repository.FindByEmail(userInfoDto.getEmail());
		if (isEmail==null){
			
			BeanUtils.copyProperties(userInfoDto, isEmail);
			user.setPassword(bcrypt.encode(userInfoDto.getPassword()));
			
			user.setCreationTime(LocalDateTime.now());
			user.setUpdateTime(LocalDateTime.now());
			user=repository.save(user);
			String mailResponse = "http://localhost:8080/user/verify/" +JwtService.generateToken(user.getUserId(),Token.WITH_EXPIRE_TIME);
			
			MailService.sendEmail(user.getEmail(),"verification", mailResponse);
			return user;
		
		}
		else {
			throw new UserException(HttpStatus.NOT_ACCEPTABLE," User already exist");

		}
	}

	@Transactional
	@Override
	public Users userVerification(String token) throws UserException {
		long id = JwtService.parse(token);
		Users userInfo = repository.findbyId(id);
		if (userInfo != null) {
			if (!userInfo.isVerified()) {
				userInfo.setVerified(true);
				repository.findbyId(userInfo.getUserId());
				return userInfo;
			} else {
				throw new UserException(HttpStatus.NOT_ACCEPTABLE," User already exist");
			}
		}
		return null;
	}

	public Users userLogin( UserLoginDto login) throws UserException {

		Users user = repository.FindByEmail(login.getEmail());
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
		Users userMail = repository.FindByEmail(email);
		// log.info("userdetails for forgetpassword" + userMail);
		if (userMail != null) {
			if (userMail.isVerified()) {
				mailObject.setEmail(userMail.getEmail());
				mailObject.setSubject("sending by admin");
				mailObject.setMessage("http://localhost:8082/updatePassword/" + JwtService.parse(email));
				return userMail;
			}
		} else {
			throw new UserException(HttpStatus.BAD_REQUEST, "Login unsuccessfull");
		}
		return null;
	}

	public boolean updatePassword(UserPasswordDto forgetpass, String token) throws UserException {
		Long id = null;	
		boolean passwordupdateflag=false;
			
		id = (Long) JwtService.parse(token);
		Users userinfo=repository.findbyId(id);
		
		if(bcrypt.matches(forgetpass.getOldPassword(),userinfo.getPassword())) {
		String epassword = bcrypt.encode(forgetpass.getCnfPassword());
		forgetpass.setCnfPassword(epassword);
		 repository.updatePassword(forgetpass, id);
			return passwordupdateflag;

		}else {
			
			throw new UserException(HttpStatus.BAD_REQUEST, "Login unsuccessfull");
		}
		

	}

}
