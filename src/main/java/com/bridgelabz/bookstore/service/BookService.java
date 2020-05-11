package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.exception.BookException;

public interface BookService {

	List<Book> displayBooks();

	Book displaySingleBook(Long id) throws BookException;

	List<Book> searchAllBooks(String title);

	List<Book> sortByPriceAsc();

	List<Book> sortByPriceDesc();

	List<Book> sortByNewest();

}
