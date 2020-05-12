package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.entity.Order;

public interface OrderService {
	Order orderTheBook(String token,Long bookId,String adressType);

	List<Order> getOrderDetails(String token);
}
