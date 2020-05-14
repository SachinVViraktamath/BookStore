package com.bridgelabz.bookstore.serviceimplemantation;



import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstore.configuration.Constants;
import com.bridgelabz.bookstore.dto.AdminDto;
import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.AdminPasswordDto;
import com.bridgelabz.bookstore.entity.Admin;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.Seller;
import com.bridgelabz.bookstore.exception.AdminException;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.ExceptionMessages;
import com.bridgelabz.bookstore.repository.AdminRepository;
import com.bridgelabz.bookstore.repository.BookRepository;
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
	private AdminRepository adminRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncryption;
	

	
	@Transactional
	@Override
	public Admin adminRegistartion(AdminDto adminInformation) throws AdminException {		
	    	Admin adminInfo = new Admin();
	    	if (adminRepository.getAdmin(adminInformation.getAdminEmailId()).isPresent()) {
			 throw new AdminException(HttpStatus.NOT_ACCEPTABLE,ExceptionMessages.EMAIL_ID_ALREADY_PRASENT);
	    	}
	    	else {
	    	String adminemial=adminInformation.getAdminEmailId();
		    BeanUtils.copyProperties(adminInformation, adminInfo);
			adminInfo.setPassword(( passwordEncryption.encode(adminInformation.getAdminPassword())));					
			adminInfo = adminRepository.save(adminInfo);
			String mailResponse = response.fromMessage(Constants.VERIFICATION_LINK,
					JwtService.generateToken(adminInfo.getAdminId(),Token.WITH_EXPIRE_TIME));		
			MailService.sendEmail(adminemial, Constants.VERIFICATION_MSG, mailResponse);	
	    	}		
		return adminInfo;
	}
	
	
	@Transactional
	@Override
	public boolean verifyAdmin(String token) throws AdminException {
		Long id = null;					
			id = JwtService.parse(token);
			Admin admininfomation=adminRepository.getAdminById(id)
			.orElseThrow(() -> new AdminException( HttpStatus.NOT_FOUND,ExceptionMessages.ADMIN_NOT_FOUND_MSG));
			if(admininfomation.isAdminIsVerified())
			{
				throw new AdminException(HttpStatus.ALREADY_REPORTED, ExceptionMessages.ALREADY_VERIFIED_EMAIL);
			}
			else {
			adminRepository.verify(id);
			}
		return true;
	}

	@Transactional
	@Override
	public Admin loginToAdmin(LoginDto information) throws AdminException {
		Admin user = adminRepository.getAdmin(information.getEmail())
				.orElseThrow(() -> new AdminException(HttpStatus.NOT_FOUND, ExceptionMessages.ADMIN_NOT_FOUND_MSG));
		if ((user.isAdminIsVerified() == true) && (passwordEncryption.matches(information.getPassword(), user.getPassword()))) {
			return user;
		} else {
			String mailResponse = response.fromMessage(Constants.VERIFICATION_LINK,
					JwtService.generateToken(user.getAdminId(),Token.WITH_EXPIRE_TIME));
			MailService.sendEmail(information.getEmail(), Constants.VERIFICATION_MSG, mailResponse);
			throw new AdminException(HttpStatus.ACCEPTED, ExceptionMessages.LOGIN_UNSUCCESSFUL);
		}
	}
	
	
	@Transactional
	@Override
	public Admin forgetPassword(String email) throws AdminException {

		Admin adminUser = adminRepository.getAdmin(email)
				.orElseThrow(() -> new AdminException( HttpStatus.NOT_FOUND,ExceptionMessages.ADMIN_NOT_FOUND_MSG));
		if (adminUser.isAdminIsVerified() == true) {
			String mailResponse = response.fromMessage(Constants.VERIFICATION_LINK,
					JwtService.generateToken(adminUser.getAdminId(),Token.WITH_EXPIRE_TIME));
			MailService.sendEmail(adminUser.getEmail(), Constants.VERIFICATION_MSG, mailResponse);
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
			id =JwtService.parse(token);
			Admin userinfo=adminRepository.getAdminById(id).orElseThrow(() -> new AdminException( HttpStatus.NOT_FOUND,ExceptionMessages.ADMIN_NOT_FOUND_MSG));                   
			if(passwordEncryption.matches(information.getOldpassword(),userinfo.getPassword())) {
			information.setConfirmPassword(passwordEncryption.encode(information.getConfirmPassword()));
			adminRepository.upDateAdminPassword(information, id);
			}else {
			throw new AdminException(HttpStatus.NOT_FOUND,ExceptionMessages.ADMIN_NOT_FOUND_MSG);		
			}
			return passwordupdateflag;
			}

    @Transactional
	@Override
	public boolean approveBook(Long BookId) throws BookException {	
		
		bookRepository.getBookById(BookId).orElseThrow(() -> new BookException( HttpStatus.NOT_FOUND,ExceptionMessages.BOOK_NOT_FOUND)); 
		boolean isbookApproved=adminRepository.approvedTheBook(BookId);
		if(isbookApproved) {
			return true;
		}else
		{
			throw new BookException(HttpStatus.NOT_FOUND,ExceptionMessages.BOOK_NOT_APPROVED);
		}
		
	}
    @Transactional
   	@Override
   	public List<Book> getNotapproveBook(String token) throws AdminException {
    	Long id =JwtService.parse(token);
    	adminRepository.getAdminById(id).orElseThrow(() -> new AdminException( HttpStatus.NOT_FOUND,ExceptionMessages.ADMIN_NOT_FOUND_MSG));                   
    	return bookRepository.getAllNotAprroveBooks();                   
   
    }



}

