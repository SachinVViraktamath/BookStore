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

	@ExceptionHandler(AdminException.class)
<<<<<<< HEAD
	public final ResponseEntity<ExceptionResponse> adminNotFoundException(AdminException ex) {
=======
	public final ResponseEntity<ExceptionResponse> adminException(AdminException ex) {
>>>>>>> 19436d95085eb1008c59bc98bc7ee0dcbc1aa5c2
		ExceptionResponse exp = new ExceptionResponse(ex.getCode(), ex.getMessage());
		return ResponseEntity.status(exp.getCode()).body(exp);
	}

	@ExceptionHandler(SellerException.class)
	public final ResponseEntity<ExceptionResponse> sellerException(SellerException ex) {
		ExceptionResponse exp = new ExceptionResponse(ex.getCode(), ex.getMessage());
		return ResponseEntity.status(exp.getCode()).body(exp);
	}

	@ExceptionHandler(UserException.class)
	public final ResponseEntity<ExceptionResponse> userException(UserException ex) {
		ExceptionResponse exp = new ExceptionResponse(ex.getCode(), ex.getMessage());
		return ResponseEntity.status(exp.getCode()).body(exp);
	}
}
