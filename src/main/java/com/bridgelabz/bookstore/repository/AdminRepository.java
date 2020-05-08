package com.bridgelabz.bookstore.repository;

import java.util.Optional;

import com.bridgelabz.bookstore.dto.UpdateAdminPassword;
import com.bridgelabz.bookstore.entity.AdminEntity;

public interface AdminRepository {

	AdminEntity save(AdminEntity adminInfromation);
	
	public Optional<AdminEntity> getAdmin(String email);
	
	boolean verify(Long id);	
	
	Optional<AdminEntity> getAdminById(Long id);

	boolean upDateAdminPassword(UpdateAdminPassword information, Long id);


	
	
}