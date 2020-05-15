package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CartDto {
   

    private double price;
	 
	
	
    private long quantity;
}
