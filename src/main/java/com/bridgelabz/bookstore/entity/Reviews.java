package com.bridgelabz.bookstore.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Reviews")
@AllArgsConstructor
@NoArgsConstructor
public class Reviews {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	private String review;
	private int rating;
	private LocalDateTime createdAt;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "reviewId")
	private List<Users> user;

}
