package com.bridgelabz.bookstore.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3AccessService {
	public String uploadFileToS3Bucket(MultipartFile multipartFile) throws FileNotFoundException,IOException;
	public boolean deleteFileFromS3Bucket() ;
}
