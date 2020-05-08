package com.bridgelabz.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstore.entity.UserData;

public interface UserRepository extends JpaRepository<UserData,Long>{
	
	

}
