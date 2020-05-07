package com.bridgelabz.bookstore.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailUtility {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEMail(String toEmail, String subject, String message) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(toEmail);
		mail.setSubject(subject);
		mail.setText(message);
		mail.setFrom("ourbookstore2020@gmail.com");
		javaMailSender.send(mail);
	}
	
}
