package com.bridgelabz.bookstore.serviceimplemantation;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.AdminDto;
import com.bridgelabz.bookstore.dto.AdminLogin;
import com.bridgelabz.bookstore.dto.UpdateAdminPassword;
import com.bridgelabz.bookstore.entity.AdminEntity;
import com.bridgelabz.bookstore.exception.AdminNotFoundException;
import com.bridgelabz.bookstore.repository.AdminRepository;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.AdminService;

@Service
public class AdminServiceImplementation implements AdminService{

	
	@Autowired
	private AdminRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncryption;
	
	@Autowired
	private MailingOperation mailObject;
	
	@Transactional
	@Override
	public AdminEntity adminRegistartion(AdminDto adminInformation) {
		
		AdminEntity adminInfo = new AdminEntity();
		if (repository.getAdmin(adminInformation.getAdminEmailId()).isPresent()==false){
			BeanUtils.copyProperties(adminInformation, adminInfo);		
			String epassword = passwordEncryption.encode(adminInformation.getAdminPassword());
			adminInfo.setAdminPassword(epassword);					
			adminInfo = repository.save(adminInfo);
			String mailResponse = response.fromMessage("http://localhost:8080/user/verify",
					generate.JwtToken(adminInfo.getAdminId()));
			mailObject.setEmail(adminInfo.getAdminEmailId());
			mailObject.setMessage(mailResponse);
			mailObject.setSubject("verification");
			MailService.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());
		} else 
		{
			return ResponseEntity.badRequest()
					.body(new Response(HttpStatus.NOT_ACCEPTABLE, "Registration Successfull", result));
			//throw new AdminNotFoundException("Email id is not exist", HttpStatus.FORBIDDEN);
		
		}
		return adminInfo;
	}
	
	
	@Transactional
	@Override
	public boolean verifyAdmin(String token) throws AdminNotFoundException {
		//System.out.println("id in verification" + (long) generate.parseJWT(token));
		Long id = (long) generate.parseJWT(token);
		repository.verify(id);
		return true;
	}

	
	
	
	
	@Transactional
	@Override
	public AdminEntity loginToAdmin(AdminLogin information) throws AdminNotFoundException {
		AdminEntity user = repository.getAdmin(information.getAdminEmailId())
				.orElseThrow(() -> new AdminNotFoundException("user is not exist", HttpStatus.NOT_FOUND));
		if ((user.isAdminIsVerified() == true) && (passwordEncryption.matches(information.getAdminPassword(), user.getAdminPassword()))) {
			return user;
		} else {
			String mailResponse = response.fromMessage("http://localhost:8080/verify",
					generate.JwtToken(user.getAdminId()));
			MailService.sendEmail(information.getAdminEmailId(), "Verification", mailResponse);
			throw new AdminNotFoundException("Login unsuccessfull", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	
	
	@Transactional
	@Override
	public AdminEntity isAdminExist(String email) throws AdminNotFoundException {

		AdminEntity adminUser = repository.getAdmin(email)
				.orElseThrow(() -> new AdminNotFoundException("user is not exist", HttpStatus.NOT_FOUND));
		if (user.isAdminIsVerified() == true) {
			String mailResponse = response.fromMessage("http://localhost:8080/verify",
					generate.JwtToken(user.getAdminId()));
			MailService.sendEmail(adminUser.getAdminEmailId(), "Verification", mailResponse);
			return adminUser;
		} else {
			return null;
		}
	}

	
	@Transactional
	@Override
	public boolean updatepassword(UpdateAdminPassword information, String token) throws AdminNotFoundException {
		Long id = null;	
		boolean passwordupdateflag=false;
		
			id = (Long) generate.parseJWT(token);
			AdminEntity userinfo=repository.getAdminById(id).orElseThrow(() -> new AdminNotFoundException("user is not exist", HttpStatus.NOT_FOUND));                   
		
		
			if(passwordEncryption.matches(information.getOldpassword(),userinfo.getAdminPassword())) {
			String epassword = passwordEncryption.encode(information.getConfirmPassword());
			information.setConfirmPassword(epassword);
			 repository.upDateAdminPassword(information, id);
			}else {
				throw new AdminNotFoundException("Email id is not exist", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
				
			}
			return passwordupdateflag;
	}



	
	
	
}

