package com.bridgelabz.bookstore.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="cart")
public class CartDetails {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long cartId;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = BookQuantity.class)
	@Column(name = "bookquantity")
	private List<BookQuantity> QuantityOfBooks;	
	

	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Book> BooksList;
	
	
}
