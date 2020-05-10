package com.bridgelabz.bookstore.serviceimplemantation;



import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.dto.AdminDto;
import com.bridgelabz.bookstore.dto.AdminLogin;
import com.bridgelabz.bookstore.dto.UpdateAdminPassword;
import com.bridgelabz.bookstore.entity.AdminEntity;
import com.bridgelabz.bookstore.exception.AdminNotFoundException;
import com.bridgelabz.bookstore.repository.AdminRepository;
import com.bridgelabz.bookstore.response.MailingOperation;
import com.bridgelabz.bookstore.response.MailingandResponseOperation;
import com.bridgelabz.bookstore.service.AdminService;
import com.bridgelabz.bookstore.utility.JwtService;
import com.bridgelabz.bookstore.utility.JwtService.Token;
import com.bridgelabz.bookstore.utility.MailService;


@Service
public class AdminServiceImplementation implements AdminService{

	@Autowired
	private MailingandResponseOperation response;
	
	@Autowired
	private MailingOperation mailObject;
	
	@Autowired
	private AdminRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncryption;
	
	

	
	@Transactional
	@Override
	public AdminEntity adminRegistartion(AdminDto adminInformation) throws AdminNotFoundException {
		
		AdminEntity adminInfo = new AdminEntity();
		if (repository.getAdmin(adminInformation.getAdminEmailId()).isPresent()==false){
			BeanUtils.copyProperties(adminInformation, adminInfo);		
			String epassword = passwordEncryption.encode(adminInformation.getAdminPassword());
			adminInfo.setAdminPassword(epassword);					
			adminInfo = repository.save(adminInfo);
			String mailResponse = response.fromMessage("http://localhost:8080/user/verify",
					JwtService.generateToken(adminInfo.getAdminId(),Token.WITH_EXPIRE_TIME));
			mailObject.setEmail(adminInfo.getAdminEmailId());
			mailObject.setMessage(mailResponse);
			mailObject.setSubject("verification");
			MailService.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());
		} else 
		{
			throw new AdminNotFoundException(HttpStatus.NON_AUTHORITATIVE_INFORMATION,"Email id is not exist");
			
			//throw new AdminNotFoundException(HttpStatus.FORBIDDEN,"Email id is not exist");
		
		}
		return adminInfo;
	}
	
	
	@Transactional
	@Override
	public boolean verifyAdmin(String token) throws AdminNotFoundException {
		//System.out.println("id in verification" + (long) generate.parseJWT(token));
		Long id = (long) JwtService.parse(token);
		repository.verify(id);
		return true;
	}

	
	
	
	
	@Transactional
	@Override
	public AdminEntity loginToAdmin(AdminLogin information) throws AdminNotFoundException {
		AdminEntity user = repository.getAdmin(information.getAdminEmailId())
				.orElseThrow(() -> new AdminNotFoundException(HttpStatus.NOT_FOUND, "user is not exist"));
		if ((user.isAdminIsVerified() == true) && (passwordEncryption.matches(information.getAdminPassword(), user.getAdminPassword()))) {
			return user;
		} else {
			String mailResponse = response.fromMessage("http://localhost:8080/verify",
					JwtService.generateToken(user.getAdminId(),Token.WITH_EXPIRE_TIME));
			MailService.sendEmail(information.getAdminEmailId(), "Verification", mailResponse);
			throw new AdminNotFoundException(HttpStatus.BAD_REQUEST, "Login unsuccessfull");
		}
	}
	
	
	
	
	
	
	
	@Transactional
	@Override
	public AdminEntity isAdminExist(String email) throws AdminNotFoundException {

		AdminEntity adminUser = repository.getAdmin(email)
				.orElseThrow(() -> new AdminNotFoundException( HttpStatus.NOT_FOUND,"user is not exist"));
		if (adminUser.isAdminIsVerified() == true) {
			String mailResponse = response.fromMessage("http://localhost:8080/verify",
					JwtService.generateToken(adminUser.getAdminId(),Token.WITH_EXPIRE_TIME));
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
		
			id = (Long) JwtService.parse(token);
			AdminEntity userinfo=repository.getAdminById(id).orElseThrow(() -> new AdminNotFoundException( HttpStatus.NOT_FOUND,"user is not exist"));                   
		
		
			if(passwordEncryption.matches(information.getOldpassword(),userinfo.getAdminPassword())) {
			String epassword = passwordEncryption.encode(information.getConfirmPassword());
			information.setConfirmPassword(epassword);
			 repository.upDateAdminPassword(information, id);
			}else {
				throw new AdminNotFoundException(HttpStatus.NON_AUTHORITATIVE_INFORMATION,"Email id is not exist");
				
			}
			return passwordupdateflag;
	}



	
	
	
}

