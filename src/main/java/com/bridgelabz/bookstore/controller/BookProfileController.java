package com.bridgelabz.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.service.BookProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstore.entity.BookProfile;
import com.bridgelabz.bookstore.exception.BookException;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.BookProfileService;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/bookImage")
@RestController
public class BookProfileController {

	@Autowired
	BookProfileService service;
	
	
	@PostMapping("/add")
	@CrossOrigin
	@ApiOperation("adding image to book")
	public ResponseEntity<Response> addProfile(@RequestBody MultipartFile file,
			@RequestHeader("bookId") Long id) throws BookException {
		BookProfile add = service.uploadFileTos3Bucket(file, id);
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "book image added Successfully", add));

	}

	/* API for update the bookImage */
	
	@PutMapping("/update")
	@CrossOrigin
	@ApiOperation("updating image of existing book")
	public ResponseEntity<Response> update(@RequestBody MultipartFile file, String fileName, String contentType,
			@RequestHeader("id") Long id) {
		BookProfile update = service.updateProfile(file, id);
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "book image updated Successfully", update));

	}

	/* API for delete the bookImage */
	@DeleteMapping("/delete")
	@CrossOrigin
	@ApiOperation("deleting  bookimage")
	public ResponseEntity<Response> deleteProfile(@RequestBody String key) {
		service.deleteobjectFromS3Bucket(key);
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "book image  removed" ));

	}
}
