package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.entity.UserBookCart;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;

public interface CartService {
	
	List<UserBookCart> addBooksInTOTheCart(String token, long bookId)throws UserException,BookException;

	List<UserBookCart> getBooksfromCart(String token)throws UserException;

	List<UserBookCart> addBooksQuantityToCart(String token, long noteId, long quantity) throws UserException, BookException;

	List<UserBookCart> removeBooksFromCart(String token, long bookId)throws UserException, BookException;

}
