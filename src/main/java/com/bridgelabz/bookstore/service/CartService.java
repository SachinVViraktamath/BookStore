package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.CartDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.CartDetails;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;

public interface CartService {

	List<CartDetails> addBooksInTOTheCart(String token, Long bookId) throws UserException, BookException;

	public List<CartDetails> getBooksfromCart(String token) throws UserException;

	List<CartDetails> removeBooksFromCart(Long bookId, String token) throws UserException, BookException;

	public List<CartDetails> addBooksQuantityToCart(String token, Long bookId, CartDto dto)
			throws UserException, BookException;

	List<CartDetails> decreasingBooksQuantityInCart(String token, Long bookId, CartDto cartdetails)
			throws UserException, BookException;
}
