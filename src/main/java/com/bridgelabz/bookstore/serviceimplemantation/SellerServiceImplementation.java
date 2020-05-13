package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.dto.SellerLoginDto;
import com.bridgelabz.bookstore.dto.SellerPasswordUpdateDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.BookQuantity;
import com.bridgelabz.bookstore.entity.Seller;
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
	
//	@Autowired
//	AmazonS3AccessService service;

	@Override
	@Transactional
	public Seller register(SellerDto dto) throws SellerException {
		Seller seller=new Seller();
		if (repository.getSeller(dto.getEmail()).isPresent()==false) {
			seller = mapper.map(dto, Seller.class);
			String epassword = encoder.encode(dto.getPassword());
			seller.setPassword(epassword);
			seller.setDateTime(LocalDateTime.now());
			repository.save(seller);
			String mailResponse = "http://localhost:8080/verify/"
					+ JwtService.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(dto.getEmail(), "verification from", mailResponse);
	
		} else 
	{
		throw new SellerException(HttpStatus.NOT_ACCEPTABLE,"seller  already exist");	
	}
	return seller;
}

	@Transactional
	@Override
	public Seller login(SellerLoginDto dto) throws SellerException {
		Seller seller = repository.getSeller(dto.getEmail())
				.orElseThrow(() -> new SellerException(HttpStatus.NOT_FOUND, "seller is not exist"));
		if ((seller.getIsVerified()==1) && (encoder.matches(dto.getPassword(), seller.getPassword()))) {
			return seller;
		} else {
			String mailResponse = "http://localhost:8080/verify/"
					+ JwtService.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(dto.getEmail(), "Verification", mailResponse);
			throw new SellerException(HttpStatus.ACCEPTED, "Login unsuccessfull");
		}
	}
	

	@Override
	@Transactional
	public Boolean verify(String token) {
		Long id = (Long) JwtService.parse(token);
		repository.verify(id);
		return true;
	}

	@Transactional
	@Override
	public List<Seller> getSellers() {
		List<Seller> sellers = repository.getSellers();
		sellers.get(0);
		return sellers;

	}

	@Override
	@Transactional
	public Book addBookBySeller(String token, BookDto dto) throws SellerException {
		Book book = new Book();
		Long sellerId = JwtService.parse(token);
		Seller seller = repository.getSellerById(sellerId)
				.orElseThrow(() -> new SellerException(HttpStatus.NOT_FOUND, "Seller is not exist"));
		
			if (seller.getIsVerified() == 1) {
				book = mapper.map(dto, Book.class);
				
			
				book.setBookCreatedAt(LocalDateTime.now());
				
				BookQuantity  quantity=new BookQuantity();
				quantity.setBookQty(dto.getBookQuantity());
				book.getBook().add(quantity);
				quantityrepo.save(quantity);
				
				seller.getSellerBooks().add(book);
				bookRepository.save(book);
				
				
				
				MailService.sendEmailToAdmin( seller.getEmail() ,book);
				
			}else {
				throw new SellerException(HttpStatus.NOT_FOUND, "Seller is not exist");
			}
		return book;
	}

	public Seller forgetPassword(String email) throws SellerException {
		Seller seller = repository.getSeller(email)
				.orElseThrow(() -> new SellerException(HttpStatus.NOT_FOUND, "seller is not exist"));
		if (seller.getIsVerified() == 1) {
			String mailResponse = "http://localhost:8080//verify/"
					+ JwtService.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(seller.getEmail(), "Verification", mailResponse);

		}
		return seller;
	}


	

	@Override
	@Transactional
	public Boolean updatePassword(SellerPasswordUpdateDto update, String token) {
		try {
			Long id = null;
			id = (Long) JwtService.parse(token);
			String epassword = encoder.encode(update.getConfirmPassword());
			String epassword1 = encoder.encode(update.getNewPassword());
			if (epassword == epassword1) {
				update.setConfirmPassword(epassword);
			
				 repository.update(update, id);}
			return true;
		
		}catch (Exception e) {
			throw new SellerException(HttpStatus.BAD_REQUEST, "updation failed");
		
		
		}}

}
