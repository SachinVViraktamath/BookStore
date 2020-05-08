package com.bridgelabz.bookstore.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.bookstore.response.ExceptionResponse;

@ControllerAdvice
public class BookStoreExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(BookNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> tokenException(BookNotFoundException ex) {	
		ExceptionResponse exp = new ExceptionResponse(ex.getCode(),ex.getMessage(),LocalDateTime.now());
		return ResponseEntity.status(exp.getCode()).body(exp);
	}
	

}
