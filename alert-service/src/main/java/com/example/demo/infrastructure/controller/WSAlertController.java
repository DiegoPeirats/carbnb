package com.example.demo.infrastructure.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import DTOs.AlertDto;

@Controller
public class WSAlertController {
	
	@MessageMapping("alert")
	@SendTo("/topic/alert")
	public AlertDto sendAlert(AlertDto message) {
		return message;
	}

}
