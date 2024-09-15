package com.electric_diary.services;

import com.electric_diary.entities.EmailEntity;

public interface EmailService {
	void sendSimpleMessage(EmailEntity object);
}
