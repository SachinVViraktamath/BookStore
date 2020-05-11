package com.bridgelabz.bookstore.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.bookstore.response.ExceptionResponse;

@ControllerAdvice
public class BookStoreExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BookException.class)
	public final ResponseEntity<ExceptionResponse> bookException(BookException ex) {
		ExceptionResponse exp = new ExceptionResponse(ex.getCode(), ex.getMessage());
		return ResponseEntity.status(exp.getCode()).body(exp);
	}

	@ExceptionHandler(AdminNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> adminNotFoundException(AdminNotFoundException ex) {
		ExceptionResponse exp = new ExceptionResponse(ex.getCode(), ex.getMessage());
		return ResponseEntity.status(exp.getCode()).body(exp);
	}

	@ExceptionHandler(SellerException.class)
	public final ResponseEntity<ExceptionResponse> sellerNotFoundException(SellerException ex) {
		ExceptionResponse exp = new ExceptionResponse(ex.getCode(), ex.getMessage());
		return ResponseEntity.status(exp.getCode()).body(exp);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> userNotFoundException(UserNotFoundException ex) {
		ExceptionResponse exp = new ExceptionResponse(ex.getCode(), ex.getMessage());
		return ResponseEntity.status(exp.getCode()).body(exp);
	}
}
