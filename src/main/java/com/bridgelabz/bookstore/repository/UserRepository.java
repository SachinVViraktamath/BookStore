package com.bridgelabz.bookstore.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.dto.UserLoginDto;
import com.bridgelabz.bookstore.dto.UserPasswordDto;
import com.bridgelabz.bookstore.entity.Users;

@Repository
@Transactional		
public interface UserRepository extends JpaRepository<Users,Long>{
	
	
		@Query(value = "insert into Users ( firstName,lastName,password,email,gender,phNo,creationTime,updateTime,isVerified) values (?,?,?,?,?,?,?,?,?)", nativeQuery = true)
		void insertData(String firstName, String lastName, String password, String email, String gender,String phNo,LocalDateTime creationTime,LocalDateTime updateTime,boolean isVerified);

		@Query(value = "select * from Users where email=?", nativeQuery = true)
		Optional<Users> FindByEmail(String email);

		@Query(value = "select * from Users where email=?", nativeQuery = true)
		Users checkByEmail(UserPasswordDto email);
		
		
		@Query(value = "update Users set password=? where user_id = ?", nativeQuery = true)
		void updatePassword(UserPasswordDto password, Long id);

		@Query(value = "select * from Users where user_id=?", nativeQuery = true)
		Optional<Users> findbyId(Long userId);

		
		
		@Query(value = "update Users set isVerified = true where user_id = ?", nativeQuery = true)
		void updateIsVerified(Long id);

		
		
}
