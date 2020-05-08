package com.bridgelabz.bookstore.dto;

public class UpdateAdminPassword {

	private String Oldpassword;
	private String newPassword;
	private String confirmPassword;
	
	public String getOldpassword() {
		return Oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		Oldpassword = oldpassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
