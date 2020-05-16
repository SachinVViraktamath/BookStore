package com.bridgelabz.bookstore.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data


@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;

	private String bookName;

	private String bookAuthor;
	
	private Long noOfBooks;
	private double bookPrice;
 
	private String bookDescription;

	private boolean isBookApproved;

	private LocalDateTime bookCreatedAt;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "bookId")
	private List<Reviews> reviewRating;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Book> BooksList;



}
