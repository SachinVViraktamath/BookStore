package com.bridgelabz.bookstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter 
@NoArgsConstructor
@Table(name="book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bookId;
	
	@Column(name="book_name",columnDefinition="varchar(80) default not null")
	private String bookName;
	
	@Column(name="book_author",columnDefinition="varchar(50) default not null")
	private String bookAuthor;
	
	@Column(name="book_price")
	private double bookPrice;
	
	@Column(name="book_image",columnDefinition="varchar(100) default not null")
	private String bookImage;
	
	@Column(name="book_description",columnDefinition="varchar(255) default not null")
	private String bookDescription;
	
	@Column(name="is_book_approved",columnDefinition="boolean default false")
	private boolean isBookApproved;
	
	@Column(name="book_quantity")
	private int bookQuantity;
	
	@ManyToMany
	private Seller seller;
	
	
	
	
	
}
