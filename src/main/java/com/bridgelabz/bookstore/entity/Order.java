package com.bridgelabz.bookstore.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name="orderBook")
public class Order {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@Column(name = "Time_of_place_the_order", nullable = false)
	private LocalDateTime orderPlaceTime;
	
	private String OrderStatus;

//	private Long quantityOfBooks;
	

	@ManyToMany(cascade = CascadeType.ALL,targetEntity = Book.class)
	@JoinColumn(name="cart_id")
	private List<CartDetails> cartBook;

}
