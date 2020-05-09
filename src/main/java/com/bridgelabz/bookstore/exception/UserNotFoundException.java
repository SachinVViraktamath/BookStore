package com.bridgelabz.bookstore.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	HttpStatus code;

	public UserNotFoundException(HttpStatus code, String message) {

		super(message);
		this.code = code;
	}

	public HttpStatus getCode() {
		return code;
	}

	
	public void setCode(HttpStatus code) {
		this.code = code;
	}
	
}
