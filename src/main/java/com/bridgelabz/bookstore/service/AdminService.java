package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.AdminDto;
import com.bridgelabz.bookstore.dto.AdminLogin;
import com.bridgelabz.bookstore.dto.UpdateAdminPassword;
import com.bridgelabz.bookstore.entity.AdminEntity;
import com.bridgelabz.bookstore.exception.AdminNotFoundException;

public interface AdminService {

	AdminEntity adminRegistartion(AdminDto adminInformation)throws AdminNotFoundException ;

	boolean verifyAdmin(String token) throws AdminNotFoundException;

	AdminEntity loginToAdmin(AdminLogin adminLogin) throws AdminNotFoundException;
	
	AdminEntity isAdminExist(String email) throws AdminNotFoundException;

	boolean updatepassword(UpdateAdminPassword information, String token) throws AdminNotFoundException;

	

	

}
