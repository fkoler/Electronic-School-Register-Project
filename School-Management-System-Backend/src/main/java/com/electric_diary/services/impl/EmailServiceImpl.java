package com.electric_diary.services.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.electric_diary.entities.EmailEntity;
import com.electric_diary.services.EmailService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class EmailServiceImpl implements EmailService {
	@PersistenceContext
	protected EntityManager entityManager;

	private final JavaMailSender javaMailSender;

	public EmailServiceImpl(final JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void sendSimpleMessage(EmailEntity object) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(object.getTo());
		message.setSubject(object.getSubject());
		message.setText(object.getText());
		javaMailSender.send(message);
	}
}
