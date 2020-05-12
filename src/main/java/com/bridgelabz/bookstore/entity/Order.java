package com.bridgelabz.bookstore.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;


@Entity
@Data
public class Order {

	
	@Id
	private Long orderId;
	
	@Column(name = "Time_of_place_the_order", nullable = false)
	private LocalDateTime orderPlaceTime;
	
	private String OrderStatus;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Book books;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Users user;
	
      
	@OneToOne(cascade = CascadeType.ALL)
	private UserAddress UserAddress;

}
