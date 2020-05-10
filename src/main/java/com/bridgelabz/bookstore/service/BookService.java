package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.entity.Book;

public interface BookService {

	public Book displaySingleBook();

	public String getBookName(long id);

	public List<Book> searchAllBooks(String jwt);

	public List<Book> sortByPriceAsc();

	public List<Book> sortByPriceDesc();

	public List<Book> sortByNewest();

	public List<Book> displayBooks();
}
