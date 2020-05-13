package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.CartDto;
import com.bridgelabz.bookstore.entity.Book;

import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;

public interface CartService {
	
	Book addBooksInTOTheCart(String token, Long bookId,CartDto  values)throws UserException,BookException;

	List<Book> getBooksfromCart(String token)throws UserException;
	
    Book removeBooksFromCart(String token, Long bookId)throws UserException, BookException;

}
