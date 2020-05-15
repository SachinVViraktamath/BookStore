package com.bridgelabz.bookstore.repository;

import java.util.Optional;

import com.bridgelabz.bookstore.dto.AdminPasswordDto;
import com.bridgelabz.bookstore.entity.Admin;
import com.bridgelabz.bookstore.entity.Book;

public interface AdminRepository {

	Admin save(Admin adminInfromation);
	
	public Optional<Admin> getAdmin(String email);
	
	boolean verify(Long id);	
	
	Optional<Admin> getAdminById(Long id);

	public boolean restAdminPassword(Admin information);
	boolean upDateAdminPassword(AdminPasswordDto information, Long id);

	boolean approvedTheBook(Long BookId);
	
	
	
}