package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class ReviewDto {

	
	private double rating;
	
	private String reviewedBy;
	private String review;
}
