package com.bridgelabz.bookstore.serviceimplemantation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.service.AmazonS3AccessService;
@Component

public class AmazonS3AccessImplementation  implements AmazonS3AccessService{

	private String awsS3AudioBucket;
	private AmazonS3 amazonS3;
	private AmazonS3Client s3;
	private String awsKeyId;
	private Region region;
	@Autowired
	public AmazonS3AccessImplementation(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider,
			String awsS3AudioBucket, String awsKeyId) {
		this.amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsProvider)
				.withRegion(awsRegion.getName()).build();
		this.awsS3AudioBucket = awsS3AudioBucket;
		this.awsKeyId = awsKeyId;
		this.region = awsRegion;

	}
	@Override
	@Async
	public String uploadFileToS3Bucket(MultipartFile multipartFile) throws FileNotFoundException,IOException {
		String image=multipartFile.getOriginalFilename();
		File file = new File(image);
	FileOutputStream fos = new FileOutputStream(file);
	fos.write(multipartFile.getBytes());
	fos.close();
	PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, image, file);
		putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
		this.amazonS3.putObject(putObjectRequest);
		String url = "https://" + this.awsS3AudioBucket + ".s3." + region + ".amazonaws.com/" + image;
		file.delete();
		return url;
		}
@Override
@Async
	public boolean deleteFileFromS3Bucket() {
		Book book=new Book();
		String url = book.getBookImage();
		String[] urlarr1 = url.split(".amazonaws.com/");
		String fileName = urlarr1[1];
		amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, fileName));
		return false;
	}

}
