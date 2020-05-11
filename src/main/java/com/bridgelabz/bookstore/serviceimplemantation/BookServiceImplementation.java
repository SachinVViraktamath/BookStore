package com.bridgelabz.bookstore.serviceimplemantation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.service.BookService;

@Service
public class BookServiceImplementation implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Book> displayBooks() {
		List<Book> books = bookRepository.getAllBooks();
		return books;
	}

	@Override
	public Book displaySingleBook(Long id) {
		Book book = bookRepository.getBookById(id);
		return book;
	}

	@Override
	public List<Book> searchAllBooks(String title) {
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

}
