package com.bridgelabz.bookstore.dto;


public class AdminDto {
	
	private String adminName;
	private String adminLastname;
	private String adminEmailId;
	private String adminPassword;
	private String adminConfirmPassword;
	private long adminPhoneNumber;	
	
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
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
	public String getAdminConfirmPassword() {
		return adminConfirmPassword;
	}
	public void setAdminConfirmPassword(String adminConfirmPassword) {
		this.adminConfirmPassword = adminConfirmPassword;
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
	private boolean adminIsVerified;
	private String adminRole;
}

