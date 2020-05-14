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
import com.bridgelabz.bookstore.dto.UserDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.ResetPassword;
import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.ExceptionMessages;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.UserAddressRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.UserService;
import com.bridgelabz.bookstore.utility.JwtService;
import com.bridgelabz.bookstore.utility.MailService;

import com.bridgelabz.bookstore.utility.JwtService.Token;

@Service
public class UserServiceImplementation implements UserService {

	/*
	 *User Service Class  is implemented by using the object reference of Repository for database,  BCryptPasswordEncoder for password
	 */
	


	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private UserAddressRepository userAddressrepository;
	
	
	
	/*********************************************************************
     * User to register with the required fields provided  
     * @param UserDto userInfoDto
     ********************************************************************/
	
	@Transactional
	@Override
	public Users register(@Valid UserDto userInfoDto) throws UserException {
		if (repository.FindByEmail(userInfoDto.getEmail()).isPresent()){
			Users user = new Users();		
			BeanUtils.copyProperties(userInfoDto, user);
			user.setPassword(bcrypt.encode(userInfoDto.getPassword()));			
			user.setCreationTime(LocalDateTime.now());
			user.setUpdateTime(LocalDateTime.now());
			user=repository.save(user);
			String mailResponse = "http://localhost:8080/user/verify/" +JwtService.generateToken(user.getUserId(),Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(user.getEmail(),"verification", mailResponse);
			throw new UserException(HttpStatus.ACCEPTED,ExceptionMessages.USER_REGISTER_STATUS); 
		}
		else {
			throw new UserException(HttpStatus.NOT_ACCEPTABLE,"User Already Registered"); 
		}
	}
	
	/*********************************************************************
     * To check whether user is verified or not by the token 
     * @param String token
     ********************************************************************/

	@Transactional
	@Override
	public Users verifyUser(String token) throws UserException {
		
		Long id =JwtService.parse(token);
		//System.out.println(id);
		Users userInfo = repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user does not exist"));
	if(userInfo.isVerified()) {
		userInfo.setVerified(true);
		repository.save(userInfo);
		throw new UserException(HttpStatus.ACCEPTED,ExceptionMessages.USER_VERIFIED_STATUS );
	}
	else {
		throw new UserException(HttpStatus.BAD_REQUEST,ExceptionMessages.USER_ALREADY_VERIFIED); 
 
	}
}
	
	/*********************************************************************
     * To login user with the required credentials 
     * @param UserLoginDto login
     ********************************************************************/
	
	@Transactional
	@Override
	public Users login(LoginDto login) throws UserException {
		Users user = repository.FindByEmail(login.getEmail()).
				orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "Please Verify Email Before Login"));
		if ((user.isVerified()) && (bcrypt.matches(login.getPassword(), user.getPassword()))) {
			throw new UserException(HttpStatus.ACCEPTED,ExceptionMessages.USER_LOGIN_STATUS); 
		} else {
			String mailResponse = "http://localhost:8080/verify" +
					JwtService.generateToken(user.getUserId(),Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(login.getEmail(), "Verification", mailResponse);
			throw new UserException(HttpStatus.ACCEPTED, "User credentials are not matched");
		}
	}
	
	

	@Transactional
	@Override
	public Users forgetPassword(String email) throws UserException {
		Users userMail = repository.FindByEmail(email).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
		// log.info("userdetails for forgetpassword" + userMail);		
			if (userMail.isVerified()) {
				String responsemail = "http://localhost:8080/verify" +
						JwtService.generateToken(userMail.getUserId(),Token.WITH_EXPIRE_TIME);
				MailService.sendEmail(userMail.getEmail(), "Verification", responsemail);
				return userMail;
			}
			throw new UserException(HttpStatus.NOT_FOUND,ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE );
	}
	
	/*********************************************************************
     * To reset password by the user with token.  
     * @param String token,UserPasswordDto restpassword
     ********************************************************************/

	@Transactional
	@Override
	public boolean resetPassword(ResetPassword restpassword, String token) throws UserException {
		boolean passwordupdateflag=false;		
		if(restpassword.getNewPassword().equals(restpassword.getConfirmPassword()))  {
		Long id =JwtService.parse(token);
		Users userinfo=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));	
		if(userinfo.isVerified()) {
		String epassword = bcrypt.encode(restpassword.getConfirmPassword());
		restpassword.setConfirmPassword(epassword);
		 repository.updatePassword(restpassword, id);
		throw new UserException(HttpStatus.ACCEPTED,"User reset password sucessfull"); 
		}
				
		throw new UserException(HttpStatus.NOT_ACCEPTABLE, "password updation failed");
		}
		return passwordupdateflag;
		
	}
	
	/*********************************************************************
     * To add addresess by the user with token.  
     * @param String token,UserAddressDto addressDto
     ********************************************************************/
	
	@Transactional
	@Override
	public UserAddress address(UserAddressDto addressDto, String token) throws UserException {
		UserAddress users=new UserAddress();
			Long id=JwtService.parse(token);
			Users user=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
			BeanUtils.copyProperties(addressDto, users);			
			user.getAddress().add(users);
			userAddressrepository.save(users);
			throw new UserException(HttpStatus.ACCEPTED,"User sucessfully added the address"); 
		
	}

	/*********************************************************************
     * To update the addresess by the user with token.  
     * @param String token,UserAddressDto addressDto,Long addressId
     ********************************************************************/
	
	@Transactional
	@Override
	public UserAddress updateAddress(String token,UserAddressDto addDto,Long addressId) throws UserException{
	//  UserAddress users=new UserAddress();
		Long id=JwtService.parse(token);
		Users user=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "user is not exist"));
		UserAddress usersaddress=user.getAddress().stream().filter((address)->address.getAddressId()==addressId).findFirst().orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "Address is not exist"));
		BeanUtils.copyProperties(addDto, usersaddress);	
		repository.save(user);		
	//	return users;	
		throw new UserException(HttpStatus.ACCEPTED,"User sucessfully updated the address"); 

	
	}
	
	
	
}