package com.electric_diary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electric_diary.entities.EmailEntity;
import com.electric_diary.services.EmailService;

@RestController
@RequestMapping(path = "/api/v1/emails")
public class EmailController {
	@Autowired
	private EmailService emailService;

	@PostMapping("/simpleEmail")
	public String sendSimpleMessage(@RequestBody EmailEntity object) {
		if (object == null || object.getTo() == null || object.getText() == null) {
			return null;
		}
		emailService.sendSimpleMessage(object);
		return "Your mail has been sent!";
	}
}