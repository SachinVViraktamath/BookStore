package com.bridgelabz.bookstore.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.bookstore.dto.AdimRestPassword;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.RegisterDto;
import com.bridgelabz.bookstore.dto.AdminPasswordDto;
import com.bridgelabz.bookstore.entity.Admin;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.BookException;


public interface AdminService {

	Admin adminRegistartion(RegisterDto adminInformation)throws AdminException  ;

	boolean verifyAdmin(String token) throws AdminException ;

	Admin loginToAdmin(LoginDto adminLogin) throws AdminException;
	
	Admin forgetPassword(String email) throws AdminException;

	boolean updatepassword(AdminPasswordDto information, String token) throws AdminException;

	boolean approveBook(Long id) throws BookException;
 	
	public boolean resetPassword(AdimRestPassword update)throws AdminException;
	
 	public List<Book> getNotapproveBook(String token) throws AdminException;

}
