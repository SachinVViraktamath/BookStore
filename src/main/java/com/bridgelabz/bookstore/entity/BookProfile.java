package com.bridgelabz.bookstore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Data
@Entity()
public class BookProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String bookImage;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	public BookProfile(String fileName, Book book) {
		this.bookImage = fileName;
		this.book = book;
	  }
}
