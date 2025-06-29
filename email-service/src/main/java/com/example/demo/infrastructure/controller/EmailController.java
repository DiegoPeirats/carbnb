package com.example.demo.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.EmailServiceImpl;

import email.EmailDetails;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class EmailController {
	
	@Autowired
	private EmailServiceImpl service;
	
	@PostMapping("/simple-mail")
	public void simpleMail(@RequestBody EmailDetails details) {
		service.sendSimpleMail(details);
	}
	
	@PostMapping("/attachment-mail")
	public void attachmentMail(@RequestBody EmailDetails details) {
		service.sendMailWithAttachment(details);
	}

}
