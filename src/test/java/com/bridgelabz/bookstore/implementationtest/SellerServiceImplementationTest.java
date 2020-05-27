package com.bridgelabz.bookstore.implementationtest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.bridgelabz.bookstore.dto.LoginDto;
import com.bridgelabz.bookstore.dto.RegisterDto;
import com.bridgelabz.bookstore.entity.Seller;
import com.bridgelabz.bookstore.service.SellerService;
import com.bridgelabz.bookstore.utility.JwtService;
import com.bridgelabz.bookstore.utility.JwtService.Token;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SellerServiceImplementationTest {
	 @Mock
	    RegisterDto sellerDto;
	    @Mock
	    SellerService sellerServiceImpl;
	    Seller seller = new Seller();
	    String token=null;
	    
	    @Test
	    public void registerSeller() throws Exception {
	    	sellerDto.setEmail("csandhyait@gmail.com");
	    	sellerDto.setName("sandy");
	    	sellerDto.setPassword("sandy");
	    	sellerDto.setMobileNumber(8087968370L);
	        Mockito.when(sellerServiceImpl.register(sellerDto)).thenReturn(seller);
	        assertThat(seller).isNotNull();
	    }
	    
	    @Test
	    public  void testLogin() throws Exception {
	        LoginDto login=new LoginDto();
	        login.setEmail("csandhyait@gmail.com");
	        login.setPassword("sandy");
	        Mockito.when(sellerServiceImpl.login(login)).thenReturn(seller);	        
	        assertThat(seller).isNotNull();
	    }

	    @Test
	    public void testVerify(){
	        seller.setSellerId(3L);
	        Mockito.when(JwtService.generateToken(3L, Token.WITH_EXPIRE_TIME)).thenReturn(token);
	        Mockito.when(sellerServiceImpl.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6M30.J7bbcxYxFXsWCWRY3DMIuSAFZ_PwgmvlOShtZh5ew2UEOFwiSnfIyon0sUYuZx5RO1VVwHvFFOhEfl5a-daIOA")).thenReturn(true);
	        assertThat(token).isNotNull();
	    }

}
