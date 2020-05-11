package com.bridgelabz.bookstore.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstore.entity.UserAddress;

@Repository
@Transactional
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

	
	@Query(value = "select * from user_address where addressId=?", nativeQuery = true)
	public UserAddress findbyId(long addressId);
	
	@Modifying
	@Query(value = "insert into user_address (street,town,district,state,country,pinCode)", nativeQuery = true)
	void addAddress( String street , String town,String district,String state,String country,int pinCode );

 	@Modifying
    @Transactional
    @Query(value = "delete from user_address where addressId = ? and userId = ?", nativeQuery = true)
    void removeAddress( long addressId, long userId );


	@Modifying
	@Transactional
	@Query(value = "update user_address set street = ?,town =? ,district = ?,state = ?, country = ? pincode = ? where userId = ? and addressId = ?", nativeQuery = true)
	void updateAdd(String street , String town,String district,String state,String country,int pinCode);

}
