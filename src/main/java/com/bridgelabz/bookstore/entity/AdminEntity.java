package com.bridgelabz.bookstore.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="admin")
public class AdminEntity {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long adminId;
	
	@Column(name = "admin_firstname", columnDefinition="varchar(80) not null" )	
	private String adminFirstname;
	
	@Column(name = "admin_lastname", columnDefinition="varchar(80) not null" )	
	private String adminLastname;
	
	@Column(name = "admin_email_id", columnDefinition="varchar(100) not null" )	
	private String adminEmailId;
	
	@Column(name = "admin_password", columnDefinition="varchar(255) not null" )	
	private String adminPassword;
	
	@Column(name = "admin_phone_number", columnDefinition="varchar(80) not null" )	
	private long adminPhoneNumber;
	
	public String getAdminFirstname() {
		return adminFirstname;
	}

	public void setAdminFirstname(String adminFirstname) {
		this.adminFirstname = adminFirstname;
	}

	
	@Column(columnDefinition = "boolean Default false", nullable = false)	
	private boolean adminIsVerified;
	private String adminRole;

	
	
	
	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}



	public String getAdminLastname() {
		return adminLastname;
	}

	public void setAdminLastname(String adminLastname) {
		this.adminLastname = adminLastname;
	}

	public String getAdminEmailId() {
		return adminEmailId;
	}

	public void setAdminEmailId(String adminEmailId) {
		this.adminEmailId = adminEmailId;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public long getAdminPhoneNumber() {
		return adminPhoneNumber;
	}

	public void setAdminPhoneNumber(long adminPhoneNumber) {
		this.adminPhoneNumber = adminPhoneNumber;
	}

	public boolean isAdminIsVerified() {
		return adminIsVerified;
	}

	public void setAdminIsVerified(boolean adminIsVerified) {
		this.adminIsVerified = adminIsVerified;
	}

	public String getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(String adminRole) {
		this.adminRole = adminRole;
	}

	

}

