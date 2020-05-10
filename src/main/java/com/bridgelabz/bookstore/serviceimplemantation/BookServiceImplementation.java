package com.bridgelabz.bookstore.serviceimplemantation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.service.BookService;

@Service
public class BookServiceImplementation implements BookService {

	@Override
	public Book displaySingleBook() {
		return null;
	}

	@Override
	public String getBookName(long id) {
		return null;
	}

	@Override
	public List<Book> searchAllBooks(String jwt) {
		return null;
	}

	@Override
	public List<Book> sortByPriceAsc() {
		return null;
	}

	@Override
	public List<Book> sortByPriceDesc() {
		return null;
	}

	@Override
	public List<Book> sortByNewest() {
		return null;
	}

	@Override
	public List<Book> displayBooks() {
		return null;
	}

}
