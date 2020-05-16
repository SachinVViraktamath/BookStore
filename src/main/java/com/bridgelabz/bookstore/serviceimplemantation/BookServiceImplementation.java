package com.bridgelabz.bookstore.serviceimplemantation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.ReviewDto;
import com.bridgelabz.bookstore.entity.Admin;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.Reviews;
import com.bridgelabz.bookstore.entity.Seller;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.ExceptionMessages;
import com.bridgelabz.bookstore.exception.SellerException;
import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.ReviewRepository;
import com.bridgelabz.bookstore.repository.SellerRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.BookService;
import com.bridgelabz.bookstore.utility.AwsS3Access;
import com.bridgelabz.bookstore.utility.JwtService;
import com.bridgelabz.bookstore.utility.MailService;

@Service
public class BookServiceImplementation implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReviewRepository reviwRepository;
	@Autowired
	ModelMapper mapper;

	@Override
	@Transactional
	public List<Book> displayBooks(Integer page) throws BookException {
		page = (page - 1) * 12;
		List<Book> books = bookRepository.getAllBooks(page);
		return books;
	}

	@Override
	@Transactional
	public Integer getCountOfBooks() {
		Integer count = bookRepository.getTotalCount();
		return count;
	}

	@Override
	@Transactional
	public Book displaySingleBook(Long id) throws BookException {
		Book book = bookRepository.getBookById(id)
				.orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND, "Book not Found Exception"));
		return book;
	}

	@Override
	@Transactional
	public List<Book> sortByPriceAsc(Integer page) throws BookException {
		page = (page - 1) * 12;
		List<Book> books = bookRepository.getByPriceAsc(page);
		return books;
	}

	@Override
	@Transactional
	public List<Book> sortByPriceDesc(Integer page) throws BookException {
		page = (page - 1) * 12;
		List<Book> books = bookRepository.getByPriceDesc(page);
		return books;
	}

	@Override
	@Transactional
	public List<Book> sortByNewest(Integer page) throws BookException {
		page = (page - 1) * 12;
		List<Book> books = bookRepository.getByDateTime(page);
		return books;
	}

	@Override
	@Transactional
	public Book addBook(String token, BookDto dto,MultipartFile file) throws SellerException, AmazonServiceException, SdkClientException, IOException {
		Book book = new Book();
		Long id = JwtService.parse(token);
		Seller seller = sellerRepository.getSellerById(id)
				.orElseThrow(() -> new SellerException(HttpStatus.NOT_FOUND, "Seller is not exist"));

		if (seller.isVerified() == true) {
			book = mapper.map(dto, Book.class);
			book.setBookCreatedAt(LocalDateTime.now());
			book.setBookimage(AwsS3Access.uploadFileTos3Bucket(file,id));
			seller.getSellerBooks().add(book);
			bookRepository.save(book);
			MailService.sendEmailToAdmin(seller.getEmail(), book);
			return book;
		} else {
			throw new SellerException(HttpStatus.NOT_FOUND, "no a verified seller ");
		}

	}

	@Override
	@Transactional
	public Book updateBook(String token, Long bookId, BookDto dto) throws BookException {
		Long id = JwtService.parse(token);
		sellerRepository.getSellerById(id)
				.orElseThrow(() -> new SellerException(HttpStatus.NOT_FOUND, "Seller is not exist"));
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND, "book is not exist exist to update"));
		book = mapper.map(dto, Book.class);
		bookRepository.save(book);
		return book;
	}

	@Override
	@Transactional
	public void writeReviewAndRating(String token, ReviewDto review, Long bookId) throws UserException, BookException {
		Long id = JwtService.parse(token);
		
		Users user = userRepository.findById(id).				
				orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "Please Verify Email Before Login"));
				Book books = bookRepository.findById(bookId)
						.orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND, "book is not exist exist to update"));
	
				boolean notExist = books.getReviewRating().stream().noneMatch(reviews -> reviews.getUser().getUserId()==id);
		if(notExist) {
			Reviews reviewdetails = new Reviews(review);
			reviewdetails.setUser(user);		
			books.getReviewRating().add(reviewdetails);				
			reviwRepository.save(reviewdetails);
			 bookRepository.save(books);
		
		}
		
	}


	@Override
	@Transactional
	public List<Reviews> getRatingsOfBook(Long bookId)  {
		Book book=null;
		try {
			book = bookRepository.findById(bookId)
					.orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND, "book is not exist exist to update"));
		} catch (BookException e) {
			e.printStackTrace();
		}
		List<Reviews> review=book.getReviewRating();
	return review;
		
	}
	
}
