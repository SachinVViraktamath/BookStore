package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.PasswordUpdate;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.SellerEntity;
import com.bridgelabz.bookstore.exception.SellerNotFoundException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.SellerRepository;
import com.bridgelabz.bookstore.response.MailResponse;
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

	private MailResponse response;
	@Autowired
	BookRepository bookRepository;
	

@Autowired
private ModelMapper mapper;
	@Override
	@Transactional
	public Boolean register(SellerDto dto) {
		SellerEntity seller = repository.getseller(dto.getEmail());
		if (seller == null) {
			seller=mapper.map(dto, SellerEntity.class);
			String epassword = encoder.encode(dto.getPassword());
			seller.setPassword(epassword);
			seller.setIsVerified(0);
			seller.setDateTime(LocalDateTime.now());
			repository.save(seller);
			String mailResponse = "http://localhost:8080/verify/"
					+ JwtService.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(dto.getEmail(), "verification from", mailResponse);
		
		}
		else 
		{
			throw new SellerNotFoundException(HttpStatus.BAD_REQUEST, "Seller is already exist");
	
		}
		return true;
	}

	@Override
	@Transactional
	public SellerEntity login(LoginDto login) {
		SellerEntity seller = repository.getseller(login.getEmail());
		if (seller != null) {
			if ((seller.getIsVerified() == 1) && (encoder.matches(login.getPassword(), seller.getPassword()))) {
				JwtService.generateToken(seller.getSellerId(), Token.WITHOUT_EXPIRE_TIME);
			}
		}
		else 
		{
			throw new SellerNotFoundException(HttpStatus.BAD_REQUEST, "login failed");
	
		}
		return seller;
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
	public List<SellerEntity> getSellers() {
		List<SellerEntity> sellers = repository.getSellers();
		SellerEntity seller = sellers.get(0);
		return sellers;

	}

	@Override
	@Transactional
	 public SellerEntity getSellerFromToken(String token) {
		Long id = JwtService.parse(token);
		SellerEntity seller=repository.getSellerById(id);
		if(seller!=null) {
		
	}else {
		throw new SellerNotFoundException(HttpStatus.BAD_REQUEST, "not a valid seller");
	}
		
		return seller;
	}

	@Override
	@Transactional
	public boolean addBookBySeller(String token, BookDto dto) {
		Book book = new Book();
Long sellerId = JwtService.parse(token);
		SellerEntity seller = repository.getSellerById(sellerId);
		if (seller.getIsVerified() == 1) {
			book=mapper.map(dto, Book.class);
				book.setBookApproved(false);
				book.setBookCreatedAt(LocalDateTime.now());
				bookRepository.save(book);
				String mailResponse = "verification of book/verifyBooks/admin/"
						+ JwtService.generateToken(book.getBookId(), Token.WITH_EXPIRE_TIME);
			}

			

		
		return true;
	}

	@Override
	@Transactional
	public Boolean bookVerify(String token, Long bookId) {
		Long id = JwtService.parse(token);
		if (repository.addBookBySeller(id, bookId) == true) {
			return true;
		} else {
		
	throw new SellerNotFoundException(HttpStatus.BAD_REQUEST, "book not verified ");
		}
	}

@Override
@Transactional
public Boolean updatePassword(PasswordUpdate update, String token) {
	try {
		Long id = null;
		id = (Long) JwtService.parse(token);
		String epassword = encoder.encode(update.getConfirmPassword());
		String epassword1 = encoder.encode(update.getNewPassword());
		if (epassword == epassword1) {
			update.setConfirmPassword(epassword);
		}
		return repository.update(update, id);
	} catch (Exception e) {
		throw new SellerNotFoundException(HttpStatus.BAD_REQUEST, "updation failed");
	}

}

}
