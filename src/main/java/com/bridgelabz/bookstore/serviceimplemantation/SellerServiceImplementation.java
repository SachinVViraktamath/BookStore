package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.SellerLoginDto;
import com.bridgelabz.bookstore.dto.SellerPasswordUpdateDto;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.Seller;
import com.bridgelabz.bookstore.exception.SellerException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.SellerRepository;
import com.bridgelabz.bookstore.service.AmazonS3AccessService;
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
	private ModelMapper mapper;
	@Autowired
	AmazonS3AccessService service;

	@Override
	@Transactional
	public Seller register(SellerDto dto) throws SellerException {

		Seller seller = repository.getSeller(dto.getEmail())
				.orElseThrow(() -> new SellerException(HttpStatus.BAD_REQUEST, "Seller is already exist"));
		if (seller == null) {
			seller = mapper.map(dto, Seller.class);
			String epassword = encoder.encode(dto.getPassword());
			seller.setPassword(epassword);
			seller.setDateTime(LocalDateTime.now());
			repository.save(seller);
			String mailResponse = "http://localhost:8080/verify/"
					+ JwtService.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(dto.getEmail(), "verification from", mailResponse);

			return seller;
		}
		return null;
	}

	@Override
	@Transactional
	public Seller login(SellerLoginDto login) {
		Seller seller = repository.getSeller(login.getEmail())
				.orElseThrow(() -> new SellerException(HttpStatus.NOT_FOUND, "seller is not exist"));
		if ((seller.getIsVerified() == 1) && (encoder.matches(login.getPassword(), seller.getPassword()))) {
			JwtService.generateToken(seller.getSellerId(), Token.WITHOUT_EXPIRE_TIME);
		} else {
			throw new SellerException(HttpStatus.BAD_REQUEST, "login failed");

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
	public List<Seller> getSellers() {
		List<Seller> sellers = repository.getSellers();
		Seller seller = sellers.get(0);
		return sellers;

	}

	@Override
	@Transactional
	public boolean addBookBySeller(String token, BookDto dto, MultipartFile multipartFile) throws SellerException {
		Book book = new Book();
		Long sellerId = JwtService.parse(token);
		System.out.println("$$$$");
		Seller seller = repository.getSellerById(sellerId)
				.orElseThrow(() -> new SellerException(HttpStatus.NOT_FOUND, "Seller is not exist"));
		try {
			if (seller.getIsVerified() == 1) {
				book = mapper.map(dto, Book.class);
				System.out.println("$$$$$$");
				book.setBookImage(service.uploadFileToS3Bucket(multipartFile));
				book.setBookCreatedAt(LocalDateTime.now());
				bookRepository.save(book);
				String mailResponse = "Books added for approval" + book;
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean bookVerify(String token, Long bookId) {
		Long id = JwtService.parse(token);
		if (repository.addBookBySeller(id, bookId) == true) {
			return true;
		} else {
			boolean delete = false;
			delete = service.deleteFileFromS3Bucket();
			throw new SellerException(HttpStatus.BAD_REQUEST, "book not verified ");
		}

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
			}
			return repository.update(update, id);
		} catch (Exception e) {
			throw new SellerException(HttpStatus.BAD_REQUEST, "updation failed");
		}

	}

}
