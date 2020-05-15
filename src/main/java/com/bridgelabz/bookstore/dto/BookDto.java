package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;

public class BookDto {

	@NotNull(message = "Book Name cannot be empty")
	private String bookName;

	@NotNull(message = "Author Name cannot be empty")
	private String bookAuthor;

	@NotNull(message = "Book Price cannot be empty")
	private double bookPrice;

//	@NotNull(message = "Book Image cannot be empty")
//	private String bookImage;

	@NotNull(message = "Book Description cannot be empty")
	private String bookDescription;

	@NotNull(message = "Book Quantity cannot be empty")
	private int noOfBooks;

	public int getNoOfBooks() {
		return noOfBooks;
	}

	public void setNoOfBooks(int noOfBooks) {
		this.noOfBooks = noOfBooks;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public double getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(double bookPrice) {
		this.bookPrice = bookPrice;
	}

//	public String getBookImage() {
//		return bookImage;
//	}
//
//	public void setBookImage(String bookImage) {
//		this.bookImage = bookImage;
//	}

	public String getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}



}
