package com.bridgelabz.bookstore.serviceimplemantation;



import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.dto.AdminDto;
import com.bridgelabz.bookstore.dto.AdminLoginDto;
import com.bridgelabz.bookstore.dto.AdminPasswordDto;
import com.bridgelabz.bookstore.entity.Admin;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.repository.AdminRepository;
import com.bridgelabz.bookstore.repository.BookRepository;
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
	private BookRepository bookRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncryption;
	
	
@Autowired
private ModelMapper mapper;
	
	@Transactional
	@Override
	public Admin adminRegistartion(AdminDto adminInformation) throws AdminException {
		
		Admin adminInfo = new Admin();
		if (repository.getAdmin(adminInformation.getAdminEmailId()).isPresent()==false){
		BeanUtils.copyProperties(adminInformation, adminInfo);
		
					
			String epassword = passwordEncryption.encode(adminInformation.getAdminPassword());
			adminInfo.setAdminPassword(epassword);					
			adminInfo = repository.save(adminInfo);
			String mailResponse = response.fromMessage("http://localhost:8080/user/verify/",
					JwtService.generateToken(adminInfo.getAdminId(),Token.WITH_EXPIRE_TIME));
			mailObject.setEmail(adminInfo.getAdminEmailId());
			mailObject.setMessage(mailResponse);
			mailObject.setSubject("verification");
			MailService.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());
		} else 
		{
			throw new AdminException(HttpStatus.NOT_ACCEPTABLE,"Admin user already exist");	
		}
		return adminInfo;
	}
	
	
	@Transactional
	@Override
	public boolean verifyAdmin(String token) throws AdminException {
				Long id = (long) JwtService.parse(token);
		repository.verify(id);
		return true;
	}

	@Transactional
	@Override
	public Admin loginToAdmin(AdminLoginDto information) throws AdminException {
		Admin user = repository.getAdmin(information.getAdminEmailId())
				.orElseThrow(() -> new AdminException(HttpStatus.NOT_FOUND, "admin is not exist"));
		if ((user.
				isAdminIsVerified() == true) && (passwordEncryption.matches(information.getAdminPassword(), user.getAdminPassword()))) {
			return user;
		} else {
			String mailResponse = response.fromMessage("http://localhost:8080/verify",
					JwtService.generateToken(user.getAdminId(),Token.WITH_EXPIRE_TIME));
			MailService.sendEmail(information.getAdminEmailId(), "Verification", mailResponse);
			throw new AdminException(HttpStatus.ACCEPTED, "Login unsuccessfull");
		}
	}
	
	
	@Transactional
	@Override
	public Admin isAdminExist(String email) throws AdminException {

		Admin adminUser = repository.getAdmin(email)
				.orElseThrow(() -> new AdminException( HttpStatus.NOT_FOUND,"admin is not exist"));
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
	public boolean updatepassword(AdminPasswordDto information, String token) throws AdminException {
		Long id = null;	
		boolean passwordupdateflag=false;		
			id = (Long) JwtService.parse(token);
			Admin userinfo=repository.getAdminById(id).orElseThrow(() -> new AdminException( HttpStatus.NOT_FOUND,"admin is not exist"));                   
			if(passwordEncryption.matches(information.getOldpassword(),userinfo.getAdminPassword())) {
			String epassword = passwordEncryption.encode(information.getConfirmPassword());
			information.setConfirmPassword(epassword);
			 repository.upDateAdminPassword(information, id);
			}else {
				throw new AdminException(HttpStatus.NOT_FOUND,"Email id is not exist");				
			}
			return passwordupdateflag;	}

    @Transactional
	@Override
	public boolean approveBook(Long BookId) throws BookException {	
		
		bookRepository.getBookById(BookId).orElseThrow(() -> new BookException( HttpStatus.NOT_FOUND,"admin is not exist"));             
		
		boolean isbookApproved=repository.approvedTheBook(BookId);
		
		if(isbookApproved==false) {
			throw new BookException(HttpStatus.NOT_FOUND,"failed to approve");
		}else
		{
			return true;
		}
		
	}
}

