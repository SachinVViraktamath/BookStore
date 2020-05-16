package com.bridgelabz.bookstore.utility;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

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
@Service
public class AwsS3Access{
	
	private  static String awsKeyId = System.getenv("aws.access.key.id");

	private  static String acessKey = System.getenv("aws.access.key.secret");

	
	
	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(awsKeyId, acessKey);
		amazonS3 = new AmazonS3Client(credentials);
	}
	
	private static AmazonS3 amazonS3;

	private static  String bucketName = System.getenv("aws.s3.audio.bucket");
	private static String region=System.getenv("aws.region");
	
/*Method for uploading the  image  in s3 bucket*/
	@Transactional
	
	public  static String uploadFileTos3Bucket(MultipartFile file,  Long id) throws AmazonServiceException, SdkClientException, IOException  {
		
				String fileName=file.getOriginalFilename();
				
				String url ="https://"+bucketName+".s3."+region+".amazonaws.com/https%3A//" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
				// profile = new BookProfile(url, book);
				
				ObjectMetadata data = new ObjectMetadata();
				
				data.setContentLength(file.getSize());
			
            PutObjectRequest putObject= new PutObjectRequest(bucketName, url, file.getInputStream(), data);

				amazonS3.putObject(bucketName, url, file.getInputStream(), data);
				
				return url;
			//	repository.addProfile(profile);
				//return profile;
			}
		

		
	
	/*Method for updating the  image  in s3 bucket*/
	@Transactional
	
	public  static String updateProfile(MultipartFile file, Long id) throws AmazonServiceException, SdkClientException, IOException {
			
				String fileName=file.getOriginalFilename();
				
				String url = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
				ObjectMetadata objectMetadata = new ObjectMetadata();
				//objectMetadata.setContentType(contentType);
				//objectMetadata.setContentLength(file.getSize());

				amazonS3.putObject(bucketName, fileName, file.getInputStream(), objectMetadata);
				
			return url;
			

	}
	/*Method for deleting the bookImage for a book in s3 bucket*/
	@Transactional
	
	public static void deleteobjectFromS3Bucket(String key) {
		try {
			amazonS3.deleteObject(bucketName, key);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}
	}

}
