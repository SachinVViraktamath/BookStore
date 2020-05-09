package com.bridgelabz.bookstore.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.dto.UpdateUserPassword;
import com.bridgelabz.bookstore.entity.UserData;


@Repository
@Transactional		
public interface UserRepository extends JpaRepository<UserData,Long>{
	
		@Query(value = "insert into user (First_Name,Last_Name,Password,Email,Gender,Phone_No,DOB,Register_Date,Last_Updated) values (?,?,?,?,?,?)", nativeQuery = true)
		void insertData(String firstName, String lastName, String password, String email, String gender,String phNo,String dob,Date creationTime,Date updateTime);

		
		@Query(value = "SELECT * from user where email=?", nativeQuery = true)
		UserData FindByEmail(String email);

		@Query(value = "select * from user where email=?", nativeQuery = true)
		UserData checkByEmail(UpdateUserPassword email);
		
		@Modifying
		@Transactional
		@Query(value = "UPDATE user set password = ? where user_Id = ?", nativeQuery = true)
		void changepassword(String password, long id);

		@Query(value = "SELECT * FROM user WHERE user_Id=?", nativeQuery = true)
		UserData findbyId(Long id);

		@Modifying
		@Transactional
		@Query(value = "update user set is_verified =  true where user_Id=?", nativeQuery = true)
		void updateIsVarified(Long id);


		

		
}
