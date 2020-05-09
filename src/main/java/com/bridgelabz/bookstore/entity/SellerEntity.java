package com.bridgelabz.bookstore.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sellerEntity")
@Getter
@Setter
public class SellerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long sellerId;
	@Column
	@NotNull
	private String sellerName;
	@Column
	@NotNull
	private String email;
	@Column
	@NotNull
	private String password;
	@Column
	@NotNull
	private String mobileNumber;
	@Column
	@NotNull
	private int isVerified;
	@Column
	private LocalDateTime dateTime;

	
	

}
