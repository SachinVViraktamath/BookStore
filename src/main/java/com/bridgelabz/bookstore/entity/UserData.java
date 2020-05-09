package com.bridgelabz.bookstore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name="User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	@Column(name = "First_Name")
	@NotNull
	@NotBlank(message = "First Name is mandatory")
	private String firstName;
	
	@Column(name = "Last_Name")
	@NotNull
	private String lastName;
	
	@Column(name = "Password")
	@NotNull
	@NotBlank(message = "Password is mandatory")
	private String password;
	
	@Column(name = "Email", unique = true)
	@NotNull
	private String email;

	@Column(name = "Gender")
	@NotBlank(message = "Gender is mandatory")
	private String gender;

	@Column(name = "Phone_No")
	@NotNull
	@NotBlank(message = "contact is mandatory")
	private long phNo;

	@Column(name = "Register_Date")
	@NotNull
	private Date creationTime;

	@Column(name = "Last_Updated")
	private Date updateTime;

	@Column(name = "is_Verified",columnDefinition = "boolean default values",nullable=false)
	private boolean isVerified;
	
}
