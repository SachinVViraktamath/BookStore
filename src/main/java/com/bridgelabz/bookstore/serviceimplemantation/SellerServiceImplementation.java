package com.bridgelabz.bookstore.serviceimplemantation;


import java.time.LocalDateTime;

import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.configuration.Constants;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.ResetPassword;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.Seller;
import com.bridgelabz.bookstore.exception.ExceptionMessages;
import com.bridgelabz.bookstore.exception.SellerException;
import com.bridgelabz.bookstore.repository.BookQuantityRepository;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.SellerRepository;
import com.bridgelabz.bookstore.service.SellerService;

import com.bridgelabz.bookstore.utility.JwtService;
import com.bridgelabz.bookstore.utility.JwtService.Token;
import com.bridgelabz.bookstore.utility.MailService;

@Service
public class SellerServiceImplementation implements SellerService {

	@Autowired
	SellerRepository repository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	BookQuantityRepository quantityrepo;
	
	@Autowired
	private ModelMapper mapper;
	

   
	@Override
	@Transactional
	public Seller register(SellerDto dto)  {
		Seller seller=new Seller();
		if(repository.getSeller(dto.getEmail()).isPresent()) {
			throw new SellerException(HttpStatus.NOT_ACCEPTABLE,ExceptionMessages.SELLER_ALREADY_MSG);
			} seller = mapper.map(dto, Seller.class);
		seller.setPassword(encoder.encode(dto.getPassword()));
		seller.setDateTime(LocalDateTime.now());
		repository.save(seller);
		String mailResponse = Constants.SELLER_VERIFICATION_LINK
				+ JwtService.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME);
		MailService.sendEmail(dto.getEmail(), Constants.SELLER_VERIFICATION_MSG, mailResponse);
	
	return seller;
}

	@Transactional
	@Override
	public Seller login(LoginDto dto)  {
		Seller seller = repository.getSeller(dto.getEmail())
				.orElseThrow(() -> new SellerException(HttpStatus.NOT_FOUND, ExceptionMessages.SELLER_NOT_FOUND_MSG));
		
		if ((seller.isVerified()==true) && (encoder.matches(dto.getPassword(), seller.getPassword()))!=true) {
			
			String mailResponse = Constants.SELLER_VERIFICATION_LINK
					+ JwtService.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(dto.getEmail(),Constants.SELLER_VERIFICATION_MSG, mailResponse);
			
			throw new SellerException(HttpStatus.ACCEPTED,ExceptionMessages.SELLER_VRFIED_YOUR_EMAIL);
			
		} 
		
		return seller;
		
	}
	

	@Override
	@Transactional
	public Boolean verify(String token) {
		Long id = (Long) JwtService.parse(token);
				Seller seller=repository.getSellerById(id).orElseThrow(() -> new SellerException(HttpStatus.BAD_REQUEST,ExceptionMessages.SELLER_NOT_FOUND_MSG));
		if(repository.verify(id)!=true)
			throw new SellerException(HttpStatus.BAD_REQUEST,ExceptionMessages.SELLER_ALREADY_VRFIED);
		
		return true;
		
	}
	

	public Seller forgetPassword(String email)  {
		Seller seller = repository.getSeller(email)
				.orElseThrow(() -> new SellerException(HttpStatus.NOT_FOUND,ExceptionMessages.SELLER_NOT_FOUND_MSG));
		if (seller.isVerified() != true) {
			throw new SellerException(HttpStatus.BAD_REQUEST,ExceptionMessages.SELLER_VRIFICATION_FAIL_MSG);
		}
		String mailResponse = Constants.SELLER_VERIFICATION_LINK
				+ JwtService.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME);
		MailService.sendEmail(email,Constants.SELLER_VERIFICATION_MSG, mailResponse);
		return seller;
	}

	@Override
	@Transactional
	public Boolean resetPassword(ResetPassword update, String token) {
			Long id =  JwtService.parse(token);
			Seller seller = repository.getSellerById(id)
					.orElseThrow(() -> new SellerException(HttpStatus.NOT_FOUND, ExceptionMessages.SELLER_NOT_FOUND_MSG));
			String epassword = encoder.encode(update.getConfirmPassword());
			String epassword1 = encoder.encode(update.getNewPassword());
			if (epassword == epassword1) {
				update.setConfirmPassword(epassword);
				 repository.update(update, id);
			return true;
			}	    
			throw new SellerException(HttpStatus.BAD_REQUEST,  ExceptionMessages.SELLER_UNMATCH_CREDENTAIL);
	     
		}

}