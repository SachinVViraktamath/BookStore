package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class PasswordUpdate {
	String NewPassword;
	String  ConfirmPassword;
	
	public String getNewPassword() {
		return NewPassword;
	}
	public void setNewPassword(String newPassword) {
		NewPassword = newPassword;
	}
	public String getConfirmPassword() {
		return ConfirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		ConfirmPassword = confirmPassword;
	}
	
}
