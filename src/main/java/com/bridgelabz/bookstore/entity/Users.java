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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="Users")
@AllArgsConstructor
@NoArgsConstructor
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	@Column(name = "First_Name")
	@NotBlank(message = "First Name is mandatory")
	private String firstName;
	
	@Column(name = "Last_Name")
	private String lastName;
	
	@Column(name = "Password")
	@NotBlank(message = "Password is mandatory")
	private String password;
	
	@Column(name = "Email", unique = true)
	private String email;

	@Column(name = "Gender")
	@NotBlank(message = "Gender is mandatory")
	private String gender;

	@Column(name = "Phone_No")
	@NotBlank(message = "contact is mandatory")
	private long phNo;

	@Column(name = "Register_Date")
	private LocalDateTime creationTime;

	@Column(name = "Last_Updated")
	private LocalDateTime updateTime;

	@Column(name = "is_Verified",columnDefinition = "boolean default values",nullable=false)
	private boolean isVerified;
	

	@OneToMany(cascade = CascadeType.ALL, targetEntity = UserAddress.class)
	@JoinColumn(name = "userId")
	private List<UserAddress> address;
	
//	@OneToOne(cascade = CascadeType.ALL, targetEntity = CartDetails.class)
//	@JoinColumn(name = "userId")
//	private List<CartDetails> cartBooks;
	
	
	
	
	
}
