package com.bridgelabz.bookstore.dto;

public class AdminLogin {

	private String adminName;
	private String adminEmailId;
	private String adminPassword;
	private long adminPhoneNumber;
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
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
}
