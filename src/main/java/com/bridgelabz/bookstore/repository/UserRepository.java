package com.bridgelabz.bookstore.repository;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.dto.UserPasswordDto;
import com.bridgelabz.bookstore.entity.Users;

@Repository
@Transactional		
public interface UserRepository extends JpaRepository<Users,Long>{
	
	
		@Query(value = "insert into user (First_Name,Last_Name,Password,Email,Gender,Phone_No,Register_Date,Last_Updated,is_Verified) values (?,?,?,?,?,?)", nativeQuery = true)
		void insertData(String firstName, String lastName, String password, String email, String gender,String phNo,LocalDateTime creationTime,LocalDateTime updateTime,boolean isVerified);

		@Query(value = "SELECT * from user where email=?", nativeQuery = true)
		Users FindByEmail(String email);

		@Query(value = "select * from user where email=?", nativeQuery = true)
		Users checkByEmail(UserPasswordDto email);
		
		@Modifying
		@Transactional
		@Query(value = "UPDATE user set password = ? where user_Id = ?", nativeQuery = true)
		void updatePassword(UserPasswordDto password, long id);

		@Query(value = "SELECT * FROM user WHERE user_Id=?", nativeQuery = true)
		Users findbyId(Long id);

		@Modifying
		@Transactional
		@Query(value = "update user set is_verified =  true where user_Id=?", nativeQuery = true)
		void updateIsVarified(Long id);

		
		
}
