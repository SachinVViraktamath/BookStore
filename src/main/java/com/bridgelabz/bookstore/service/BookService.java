package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.entity.Book;

public interface BookService {

	List<Book> displayBooks();

	Book displaySingleBook(Long id);

	List<Book> searchAllBooks(String title);

	List<Book> sortByPriceAsc();

	List<Book> sortByPriceDesc();

	List<Book> sortByNewest();

}
