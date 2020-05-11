package com.bridgelabz.bookstore.serviceimplemantation;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.service.BookService;

@Service
public class BookServiceImplementation implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	@Transactional
	public List<Book> displayBooks() throws BookException {
		List<Book> books = bookRepository.getAllBooks();
		if (books != null)
			return books;
		else
			throw new BookException(HttpStatus.NOT_FOUND, "No Books to display");

	}

	@Override
	@Transactional
	public Book displaySingleBook(Long id) throws BookException {
		Book book = bookRepository.getBookById(id)
				.orElseThrow(() -> new BookException(HttpStatus.NOT_FOUND, "Book not Found Exception"));
		return book;
	}

	@Override
	@Transactional
	public List<Book> sortByPriceAsc() throws BookException {
		List<Book> books = bookRepository.getAllBooks();
		if (books != null)
			return books.stream().sorted(Comparator.comparing(Book::getBookName)).collect(Collectors.toList());
		else
			throw new BookException(HttpStatus.NOT_FOUND, "No Books to display");

	}

	@Override
	@Transactional
	public List<Book> sortByPriceDesc() throws BookException {
		List<Book> books = bookRepository.getAllBooks();
		if (books != null)
			return books.stream().sorted(Comparator.comparing(Book::getBookName).reversed())
					.collect(Collectors.toList());
		else
			throw new BookException(HttpStatus.NOT_FOUND, "No Books to display");
	}

	@Override
	@Transactional
	public List<Book> sortByNewest() throws BookException {
		List<Book> books = bookRepository.getAllBooks();
		if (books != null)
			return books.stream().sorted(Comparator.comparing(Book::getBookCreatedAt).reversed())
					.collect(Collectors.toList());
		else
			throw new BookException(HttpStatus.NOT_FOUND, "No Books to display");

	}

}
