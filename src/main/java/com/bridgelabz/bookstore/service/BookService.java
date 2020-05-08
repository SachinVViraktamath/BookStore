package com.bridgelabz.bookstore.service;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.entity.Book;

public interface BookService {


	public boolean sellerAddingBooks(BookDto bookdto);

	public List<Book> displayBooksForUser(String jwt);

	public Book displaySingleBookForUser(String jwt);

	public String getBookName(long id);
}
