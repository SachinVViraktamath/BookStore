package com.bridgelabz.bookstore.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
public class UserBookCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;
		
	private LocalDateTime DateOfbookaddedToCart;

	@Column(name = "bookquantity") 
	private Long bookQuantity;
	

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Book> BooksList;

}
