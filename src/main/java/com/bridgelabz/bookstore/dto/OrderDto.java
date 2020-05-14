package com.bridgelabz.bookstore.dto;


import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.bridgelabz.bookstore.entity.Book;
import lombok.Data;


@Data
public class OrderDto {


	@Id
	private Long orderId;
	
	@Column(name = "time_of_oder_placed", nullable = false)
	private LocalDateTime TimeOfOrderPlaced;
	
	private Long quantityOfBooks;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Book> BooksList;


	
}
