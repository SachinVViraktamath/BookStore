package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor
@Value
@Data
public class BookDto {

	@NotNull(message = "Book Name cannot be empty")
	private String bookName;

	@NotNull(message = "Author Name cannot be empty")
	private String bookAuthor;

	@NotNull(message = "Book Price cannot be empty")
	private double bookPrice;

	@NotNull(message = "Book Image cannot be empty")
	private String bookImage;

	@NotNull(message = "Book Description cannot be empty")
	private String bookDescription;

	@NotNull(message = "Book Quantity cannot be empty")
	private String bookQuantity;
}
