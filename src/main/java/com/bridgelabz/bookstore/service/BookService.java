package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.exception.BookException;

public interface BookService {

	List<Book> displayBooks() throws BookException;

	Book displaySingleBook(Long id) throws BookException;

	List<Book> sortByPriceAsc() throws BookException;

	List<Book> sortByPriceDesc() throws BookException;

	List<Book> sortByNewest() throws BookException;

}
