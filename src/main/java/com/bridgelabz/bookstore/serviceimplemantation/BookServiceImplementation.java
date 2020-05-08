package com.bridgelabz.bookstore.serviceimplemantation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.service.BookService;

@Service
public class BookServiceImplementation implements BookService {

	@Override
	public boolean sellerAddingBooks(BookDto bookdto) {
		return false;
	}

	@Override
	public List<Book> displayBooksForUser(String jwt) {
		return null;
	}

	@Override
	public Book displaySingleBookForUser(String jwt) {
		return null;
	}

	@Override
	public String getBookName(long id) {
		return null;
	}

}
