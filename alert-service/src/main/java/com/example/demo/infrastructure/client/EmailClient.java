package com.example.demo.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import email.EmailDetails;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name="email-client", path="/email-client")
public interface EmailClient {
	
	@PostMapping("/simple-mail")
	public void simpleMail(@RequestBody EmailDetails details);
	
	@PostMapping("/attachment-mail")
	public void attachmentMail(@RequestBody EmailDetails details);

}
