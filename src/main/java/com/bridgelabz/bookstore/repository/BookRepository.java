package com.bridgelabz.bookstore.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.entity.Book;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {

	@Query(value = "select * from book", nativeQuery = true)
	public List<Book> getAllBooks();

	//Book By Id
	@Query(value = "select * from book where book_id=?1", nativeQuery = true)
	public Optional<Book> getBookById(Long id);

}
