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
//@Getter
//@Setter
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
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public int getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(int isVerified) {
		this.isVerified = isVerified;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	
	

}
