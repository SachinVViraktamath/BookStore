package com.bridgelabz.bookstore.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;

	private String bookName;

	private String bookAuthor;

	private double bookPrice;

	private String bookImage;

	private String bookDescription;

	private boolean isBookApproved;

	private LocalDateTime bookCreatedAt;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "bookId")
	private List<Reviews> reviews;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bookId")
	private List<BookQuantity> bookquantity;
}
