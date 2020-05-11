package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.AdminDto;
import com.bridgelabz.bookstore.dto.AdminLoginDto;
import com.bridgelabz.bookstore.dto.AdminPasswordDto;
import com.bridgelabz.bookstore.entity.Admin;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.BookException;


public interface AdminService {

	Admin adminRegistartion(AdminDto adminInformation)throws AdminException ;

	boolean verifyAdmin(String token) throws AdminException;

	Admin loginToAdmin(AdminLoginDto adminLogin) throws AdminException;
	
	Admin isAdminExist(String email) throws AdminException;

	boolean updatepassword(AdminPasswordDto information, String token) throws AdminException;

	boolean approveBook(Long email) throws BookException;
	

	

}
