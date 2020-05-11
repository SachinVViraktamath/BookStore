package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.entity.UserBookCart;

public interface CartService {
	
	List<UserBookCart> addBooksInTOTheCart(String token, long bookId);

	List<UserBookCart> getBooksfromCart(String token);

	List<UserBookCart> addBooksQuantityToCart(String token, long noteId, long quantity);

	List<UserBookCart> removeBooksFromCart(String token, long bookId);

}
