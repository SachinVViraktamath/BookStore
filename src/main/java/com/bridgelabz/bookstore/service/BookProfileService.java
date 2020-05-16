package com.bridgelabz.bookstore.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstore.entity.BookProfile;
import com.bridgelabz.bookstore.exception.BookException;

public interface BookProfileService {
	public BookProfile uploadFileTos3Bucket(MultipartFile file, Long id)throws BookException;

	public BookProfile updateProfile(MultipartFile file, Long id);

	public void deleteobjectFromS3Bucket(String key);
}
