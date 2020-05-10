package com.bridgelabz.bookstore.serviceimplemantation;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.SellerDto;
import com.bridgelabz.bookstore.entity.SellerEntity;
import com.bridgelabz.bookstore.repository.SellerRepository;
import com.bridgelabz.bookstore.response.MailResponse;
import com.bridgelabz.bookstore.service.SellerService;
import com.bridgelabz.bookstore.utility.JwtService;
import com.bridgelabz.bookstore.utility.JwtService.Token;
import com.bridgelabz.bookstore.utility.MailService;

@Service
public class SellerServiceImplementation implements SellerService {

	@Autowired
	SellerRepository repository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	private MailResponse response;


	@Override
	@Transactional
	public Boolean register(SellerDto dto) {
		SellerEntity seller = repository.getseller(dto.getEmail());
		if (seller == null) {
			BeanUtils.copyProperties(dto, SellerEntity.class);
			String epassword = encoder.encode(dto.getPassword());
			seller.setPassword(epassword);
			seller.setIsVerified(0);
			seller.setDateTime(LocalDateTime.now());
			System.out.println("@@@@@@");
			repository.save(seller);
			String mailResponse = "http://localhost:8080 verify"
					+ JwtService.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(dto.getEmail(), "verification from", mailResponse);

			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public SellerEntity login(LoginDto login) {
		SellerEntity seller = repository.getseller(login.getEmail());
		if (seller != null) {
			if ((seller.getIsVerified() == 1) && (encoder.matches(login.getPassword(), seller.getPassword()))) {
				JwtService.generateToken(seller.getSellerId(), Token.WITHOUT_EXPIRE_TIME);
			}
		}
		return seller;
	}

	@Override
	@Transactional
	public Boolean verify(String token) {
		Long id = (Long) JwtService.parse(token);
		repository.verify(id);
		return true;
	}

	@Transactional
	@Override
	public List<SellerEntity> getSellers() {
		List<SellerEntity> sellers = repository.getSellers();
		SellerEntity seller = sellers.get(0);
		return sellers;

	}

	@Override
	@Transactional
	public Long getSellerIdFromToken(String token) {
		Long id = JwtService.parse(token);
		return id;
	}

}
