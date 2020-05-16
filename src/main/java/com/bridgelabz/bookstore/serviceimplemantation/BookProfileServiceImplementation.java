package com.bridgelabz.bookstore.serviceimplemantation;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.BookProfile;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.exception.SellerException;
import com.bridgelabz.bookstore.repository.BookImageRespository;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.service.BookProfileService;
@Service
public class BookProfileServiceImplementation implements BookProfileService{
	
	private String awsKeyId = System.getenv("aws.access.key.id");

	private String acessKey = System.getenv("aws.access.key.secret");

	
	
	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(awsKeyId, acessKey);
		amazonS3 = new AmazonS3Client(credentials);
	}
	
	private AmazonS3 amazonS3;
	@Autowired
	private BookImageRespository repository;

	@Autowired
	
	private BookRepository bookRepository;

	private String bucketName = System.getenv("aws.s3.audio.bucket");
	private String region=System.getenv("aws.region");
	
/*Method for uploading the book image for a book in s3 bucket*/
	@Transactional
	@Override
	public BookProfile uploadFileTos3Bucket(MultipartFile file,  Long id) throws BookException {
		try {
			Book book=bookRepository.findById(id).orElseThrow(() -> new BookException (HttpStatus.NOT_FOUND, "book is not exist"));

			if (book != null) {
				String fileName=file.getOriginalFilename();
				
				String url = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
				BookProfile profile = new BookProfile(url, book);
				
				ObjectMetadata data = new ObjectMetadata();
				
				data.setContentLength(file.getSize());
			
            PutObjectRequest putObject= new PutObjectRequest(bucketName, url, file.getInputStream(), data);

				amazonS3.putObject(bucketName, url, file.getInputStream(), data);
				
				profile.setBook(book);
				repository.addProfile(profile);
				return profile;
			}
		} catch (SdkClientException | IOException e) {

			throw new RuntimeException("can't upload the book image ");

		}

	return null;	
	}
	/*Method for updating the book image for a book in s3 bucket*/
	@Transactional
	@Override
	public BookProfile updateProfile(MultipartFile file, Long id) {

		try {
			Book book=bookRepository.findById(id).orElseThrow(() -> new BookException (HttpStatus.NOT_FOUND, "book is not exist"));

			BookProfile profile = repository.findUserById(id);
			if (book != null && profile != null) {
				String fileName=file.getOriginalFilename();
				deleteobjectFromS3Bucket(profile.getBookImage());
				repository.delete(profile);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				//objectMetadata.setContentType(contentType);
				//objectMetadata.setContentLength(file.getSize());

				amazonS3.putObject(bucketName, fileName, file.getInputStream(), objectMetadata);
				repository.addProfile(profile);
				return profile;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*Method for deleting the bookImage for a book in s3 bucket*/
	@Transactional
	@Override
	public void deleteobjectFromS3Bucket(String key) {
		try {
			amazonS3.deleteObject(bucketName, key);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}

}
