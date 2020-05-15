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
import com.bridgelabz.bookstore.configuration.Constants;
import com.bridgelabz.bookstore.dto.AdminPasswordDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.ResetPassword;
import com.bridgelabz.bookstore.dto.UserAddressDto;
import com.bridgelabz.bookstore.entity.Admin;
import com.bridgelabz.bookstore.entity.UserAddress;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.AdminException;
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
		Users user = new Users();		
		if (repository.FindByEmail(userInfoDto.getEmail()).isPresent()){
			 throw new UserException(HttpStatus.NOT_ACCEPTABLE,ExceptionMessages.EMAIL_ID_ALREADY_PRASENT);
		}
			BeanUtils.copyProperties(userInfoDto, user);
			user.setPassword(bcrypt.encode(userInfoDto.getPassword()));			
			user.setCreationTime(LocalDateTime.now());
			user.setUpdateTime(LocalDateTime.now());
			user=repository.save(user);
			String mailResponse = Constants.USER_VERIFICATION_LINK +JwtService.generateToken(user.getUserId(),Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(user.getEmail(),Constants.USER_VERIFICATION_MSG, mailResponse);
			return user;
}
	
	/*********************************************************************
     * To check whether user is verified or not by the token 
     * @param String token
     ********************************************************************/

	@Transactional
	@Override
	public boolean verifyUser(String token) throws UserException {
		
		Long id =JwtService.parse(token);
		//System.out.println(id);
		Users userInfo = repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
	if(userInfo.isVerified()!=true) {
		userInfo.setVerified(true);
		repository.save(userInfo);
		throw new UserException(HttpStatus.ACCEPTED,ExceptionMessages.USER_VERIFIED_STATUS );
	}
	
		throw new UserException(HttpStatus.BAD_REQUEST,ExceptionMessages.USER_ALREADY_VERIFIED); 
 
}
	
	/*********************************************************************
     * To login user with the required credentials 
     * @param UserLoginDto login
     ********************************************************************/
	
	@Transactional
	@Override
	public Users login(LoginDto login) throws UserException {
		Users user = repository.FindByEmail(login.getEmail()).
				orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		if ((user.isVerified()==true) && (bcrypt.matches(login.getPassword(), user.getPassword()))) {
			throw new UserException(HttpStatus.ACCEPTED,ExceptionMessages.USER_LOGIN_STATUS); 
		} 
		String mailResponse =Constants.USER_VERIFICATION_LINK +
					JwtService.generateToken(user.getUserId(),Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(login.getEmail(), Constants.USER_VERIFICATION_MSG, mailResponse);
			throw new UserException(HttpStatus.ACCEPTED, ExceptionMessages.USER_FAILED_LOGIN_STATUS);
	}
	
	

	@Transactional
	@Override
	public Users forgetPassword(String email) throws UserException {
		Users userMail = repository.FindByEmail(email).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		// log.info("userdetails for forgetpassword" + userMail);		
			if (userMail.isVerified()==true) {
				String responsemail = Constants.USER_VERIFICATION_LINK+
						JwtService.generateToken(userMail.getUserId(),Token.WITH_EXPIRE_TIME);
				MailService.sendEmail(userMail.getEmail(), Constants.USER_VERIFICATION_MSG, responsemail);
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
	public boolean resetPassword(ResetPassword information, String token) throws UserException {
		Long id = null;	
		boolean passwordupdateflag=false;		
			id =JwtService.parse(token);
			Users userinfo=repository.findbyId(id).orElseThrow(() -> new UserException( HttpStatus.NOT_FOUND,ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));                   
			if(bcrypt.matches(information.getConfirmPassword(),userinfo.getPassword())!=true) {
			throw new UserException(HttpStatus.NOT_FOUND,ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE);
			}
			information.setConfirmPassword(bcrypt.encode(information.getConfirmPassword()));
			repository.updatePassword(information, id);		
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
			Users user=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
			BeanUtils.copyProperties(addressDto, users);			
			user.getAddress().add(users);
			userAddressrepository.save(users);
			throw new UserException(HttpStatus.ACCEPTED,ExceptionMessages.USER_ADDRESS_STATUS); 
		
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
		Users user=repository.findbyId(id).orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		UserAddress usersaddress=user.getAddress().stream().filter((address)->address.getAddressId()==addressId).findFirst().orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "Address is not exist"));
		BeanUtils.copyProperties(addDto, usersaddress);	
		repository.save(user);		
		throw new UserException(HttpStatus.ACCEPTED,ExceptionMessages.USER_UPDATE_ADDRESS_MESSAGE); 

	
	}
}