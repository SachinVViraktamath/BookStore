package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CartDto {
   
	@NotNull(message = "Name field can't be empty!!!")	
    private double price;
	 
	
	@NotNull(message = "Name field can't be empty!!!")
    private int quantity;
}
