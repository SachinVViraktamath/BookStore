package com.bridgelabz.bookstore.service;



import com.bridgelabz.bookstore.entity.Order;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.UserException;

public interface OrderService {
	Order orderTheBook(String token,Long bookId,String adressType) throws UserException ,BookException;

}
