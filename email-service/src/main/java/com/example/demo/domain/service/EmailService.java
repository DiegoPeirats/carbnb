package com.example.demo.domain.service;

import email.EmailDetails;

public interface EmailService {
	
	void sendSimpleMail(EmailDetails emailDetails);
	
	void sendMailWithAttachment(EmailDetails emailDetails);

}
