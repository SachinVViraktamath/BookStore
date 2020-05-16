package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.ReviewDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.Reviews;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.SellerException;
import com.bridgelabz.bookstore.exception.UserException;

public interface BookService {

	List<Book> displayBooks(Integer page) throws BookException;

	Book displaySingleBook(Long id) throws BookException;

	public Book updateBook(String token, Long bookId, BookDto dto) throws BookException;

	public Book addBook(String token, BookDto dto) throws SellerException;

	public void writeReviewAndRating(String token, ReviewDto rrDTO, Long bookId) throws UserException, BookException;

	public List<Reviews> getRatingsOfBook(Long bookId);

	List<Book> sortByPriceAsc(Integer page) throws BookException;

	List<Book> sortByPriceDesc(Integer page) throws BookException;

	List<Book> sortByNewest(Integer page) throws BookException;

	Integer getCountOfBooks();
}
