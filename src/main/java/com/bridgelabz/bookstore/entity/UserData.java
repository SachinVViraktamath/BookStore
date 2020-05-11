package com.bridgelabz.bookstore.entity;

import java.util.Date;
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
//@Getter
//@Setter
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
	

	@OneToMany(cascade = CascadeType.ALL, targetEntity = UserAddress.class)
	@JoinColumn(name = "userId")
	private List<UserAddress> address;
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getPhNo() {
		return phNo;
	}

	public void setPhNo(long phNo) {
		this.phNo = phNo;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	
	
}
